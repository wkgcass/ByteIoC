package net.cassite.byteioc.annoreader.handlers;

import javassist.CtClass;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.TypeAnnotationHandler;
import net.cassite.byteioc.annoreader.TypeChainHandler;
import net.cassite.byteioc.annoreader.annotations.Singleton;
import net.cassite.byteioc.dependencies.BClass;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * handler for annotation @Singleton
 */
public class SingletonTypeHandler implements TypeAnnotationHandler {
        @Override
        public String handle(String name, CtClass aClass, Collection<Annotation> annotations, TypeChainHandler chain, Helper helper) throws Exception {
                String nameFromChain = chain.handle(name, aClass, annotations, chain, helper);
                helper.getDependencies().addSingletonClass(new BClass(aClass.getName()), helper.getInstanceNameByClass(aClass.getName()));

                return nameFromChain;
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return Helper.containsAnnotation(Singleton.class, annotations);
        }
}
