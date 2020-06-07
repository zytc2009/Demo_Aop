package com.zy.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zy.binddata.annotations.BindData;
import com.zy.binddata.annotations.Keep;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;



public class Processor extends AbstractProcessor {
    private Filer filer;
    private Messager messager;
    private Elements elementUtils;

    //每个存在注解的类，key:package_classname value:被注解的类型元素
    private Map<String, List<Element>> annotationClassMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            buildAnnotatedElement(roundEnv, BindData.class);
        }else {
            System.out.println("Processor " + annotationClassMap.size());
            for (Map.Entry<String, List<Element>> entry : annotationClassMap.entrySet()) {
                String packageName = entry.getKey().split("_")[0];
                String typeName = entry.getKey().split("_")[1];
                ClassName className = ClassName.get(packageName, typeName);


                System.out.println("Processor " +entry.getKey());

                ClassName generatedClassName = ClassName
                        .get(packageName, typeName + "ParseHelper");

                //create class：name + ParseHelper,  keep
                System.out.println("Processor " +generatedClassName );
                TypeSpec.Builder classBuilder = TypeSpec.classBuilder(generatedClassName)
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(Keep.class);

                //example:
//                public static void bindData(JSONObject data, UserData bean) {
//
//                }

                ClassName jsonObjectClassName = ClassName.get(
                        NameStore.Package.ANDROID_JSON,
                        NameStore.Class.ANDROID_JSONOBJECT);

                MethodSpec.Builder bindViewsMethodBuilder = MethodSpec
                        .methodBuilder(NameStore.Method.BIND_DATA)
                        .addModifiers(Modifier.PUBLIC)
                        .addModifiers(Modifier.STATIC)
                        .returns(void.class)
                        .addParameter(jsonObjectClassName, NameStore.Variable.ANDROID_DATA)
                        .addParameter(className, NameStore.Variable.ANDROID_BEAN);

                /* GradeData:
                 * bean.gradeName = data.optXXX("name");
                 * bean.userData = new com.szy.aop.bean.UserData().parseData(data);
                 * */
                for (VariableElement variableElement : ElementFilter.fieldsIn(entry.getValue())) {
                    BindData bindView = variableElement.getAnnotation(BindData.class);
                    System.out.println("Processor variableElement=" +ClassName.get(variableElement.asType()));
                    if (bindView != null) {
                        bindDataItem(bindViewsMethodBuilder,variableElement.getSimpleName().toString(),ClassName.get(variableElement.asType()).toString(), bindView.value());
                    }
                }
                //将构建出来的方法添加到类里面
                classBuilder.addMethod(bindViewsMethodBuilder.build());
                //
                try {
                    JavaFile.builder(packageName,
                            classBuilder.build())
                            .build()
                            .writeTo(filer);
                } catch (IOException e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, e.toString());
                }
            }
        }
        return true;
    }


    private void bindDataItem(MethodSpec.Builder bindViewsMethodBuilder, String fieldName, String type, String bindValue){
        System.out.println("Processor bindDataItem() type=" + type);
        if(isPrimitive(type)){
            //基本数据类型，做强转
            bindViewsMethodBuilder.addStatement(NameStore.Variable.ANDROID_BEAN + ".$N = ($N) " + NameStore.Variable.ANDROID_DATA + ".opt(\"$L\")",
                    fieldName,
                    type,
                    bindValue);
        }else if(type.startsWith("java.util.List") || type.startsWith("java.util.ArrayList") ){
            //List处理。TODO
            //1.获取子类型，先不考虑嵌套
            String componentType = type.substring(type.indexOf("<")+1, type.lastIndexOf(">"));
            System.out.println("Processor bindDataItem() componentType=" + componentType);
            /**
             * JSONArray array = data.optJSONArray("grades");
             * java.util.List<type> result = new java.util.ArrayList<>(array.length());
             * if(array != null && array.length() >0){
             *          for(int i=0;i<array.length();i++){
             *                JSONObject object = array.optJSONObject(i);
             *                //类型处理
             *                result.add(object);
             *          }
             * }
             *
             */
            //遍历Array
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("org.json.JSONArray array = %s.optJSONArray(\"%s\");", NameStore.Variable.ANDROID_DATA, bindValue));

            System.out.println("Processor bindDataItem() 1");
            if(isPrimitive(componentType)){
                builder.append(String.format("java.util.List<%s> result = new java.util.ArrayList<>(array.length());", getBoxType(componentType)));
            }else{
                builder.append(String.format("java.util.List<%s> result = new java.util.ArrayList<>(array.length());", componentType ));
            }

            builder.append(String.format("if(array != null && array.length() >0){"));
            builder.append(String.format(" for(int i=0;i<array.length();i++){"));

            if(isPrimitive(componentType)) {
                builder.append(String.format("  %s object = (%s) array.opt(i);", componentType, componentType));
            }else if(type.contains("aop.bean")){
                builder.append(String.format("  %s  object = new %s().parseData( array.optJSONObject(i));", componentType, componentType)) ;
            }else{
                builder.append(String.format("  %s  object =array.opt%s(i);", componentType, componentType.substring(componentType.lastIndexOf(".")+1)));
            }
            builder.append("result.add(object);");
            builder.append("} } ");

            bindViewsMethodBuilder.addStatement(builder.toString());

        }else if(type.contains("aop.bean")){
            //自定义数据必须自己实现parseData方法
            bindViewsMethodBuilder.addStatement(NameStore.Variable.ANDROID_BEAN + ".$N =new $N().parseData($L)",
                    fieldName,
                    type,
                    NameStore.Variable.ANDROID_DATA);
        }else {//其他:String,JSONObject,JSONArray
            bindViewsMethodBuilder.addStatement(NameStore.Variable.ANDROID_BEAN + ".$N =" + NameStore.Variable.ANDROID_DATA + ".opt$N(\"$L\")",
                    fieldName,
                    type.substring(type.lastIndexOf(".")+1),
                    bindValue);
        }

    }

    private boolean isPrimitive(String type){
        if(type.equals("int") || type.equals("double") || type.equals("long")
                || type.equals("float") || type.equals("short") || type.equals("char") ||
                type.equals("boolean")  ){
            return true;
        }
        return false;
    }

    //
    private String getBoxType(String type){
        switch (type){
            case "boolean":
                return "java.lang.Boolean";
            case "int":
                return "java.lang.Integer";
            case "double":
                return "java.lang.Double";
            case "long":
                return "java.lang.Long";
            case "float":
                return "java.lang.Float";
            case "short":
                return "java.lang.Short";
            case "char":
                return "java.lang.Character";
        }
        return "";
    }


    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new TreeSet<>(Arrays.asList(
                BindData.class.getCanonicalName(),
                Keep.class.getCanonicalName()));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void buildAnnotatedElement(RoundEnvironment roundEnv, Class<? extends Annotation> clazz) {
        for (Element element : roundEnv.getElementsAnnotatedWith(clazz)) {
            String className = getFullClassName(element);
            List<Element> cacheElements = annotationClassMap.get(className);
            if (cacheElements == null) {
                cacheElements = new ArrayList<>();
                annotationClassMap.put(className, cacheElements);
            }
            cacheElements.add(element);
        }
    }

    private String getFullClassName(Element element) {
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        System.out.println("Processor typeElement=" +typeElement);
        return packageName + "_" + typeElement.getSimpleName().toString();
    }
}
