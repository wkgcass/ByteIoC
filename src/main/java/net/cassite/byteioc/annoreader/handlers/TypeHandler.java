package net.cassite.byteioc.annoreader.handlers;

import javassist.CtClass;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.TypeAnnotationHandler;
import net.cassite.byteioc.annoreader.TypeChainHandler;
import net.cassite.byteioc.annoreader.annotations.WireType;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * default type annotation handler
 */
public class TypeHandler implements TypeAnnotationHandler {
        @Override
        public String handle(String name, CtClass aClass, Collection<Annotation> annotations, TypeChainHandler chain, Helper helper) throws Exception {
                String nameFromChain = chain.handle(name, aClass, annotations, chain, helper);
                if (!Helper.resultChanged(name, nameFromChain) && nameFromChain == null) {
                        nameFromChain = helper.generateName();
                }
                AutoWireTypeHandler.putCtClass(aClass, WireType.SETTERS);
                return nameFromChain;
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return true;
        }
}
