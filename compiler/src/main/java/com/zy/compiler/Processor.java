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
                System.out.println("Processor " +generatedClassName);
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
                         if(ClassName.get(variableElement.asType()).toString().contains("aop.bean")){
                             //
                             bindViewsMethodBuilder.addStatement(NameStore.Variable.ANDROID_BEAN + ".$N =new $T().parseData($L)",
                                     variableElement.getSimpleName(),
                                     ClassName.get(variableElement.asType()),
                                     NameStore.Variable.ANDROID_DATA);
                         }else {
                             bindViewsMethodBuilder.addStatement(NameStore.Variable.ANDROID_BEAN + ".$N =" + NameStore.Variable.ANDROID_DATA + ".opt$T(\"$L\")",
                                     variableElement.getSimpleName(),
                                     variableElement,
                                     bindView.value());
                         }
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
