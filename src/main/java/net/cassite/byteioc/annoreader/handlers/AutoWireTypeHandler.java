package net.cassite.byteioc.annoreader.handlers;

import javassist.CtClass;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.TypeAnnotationHandler;
import net.cassite.byteioc.annoreader.TypeChainHandler;
import net.cassite.byteioc.annoreader.annotations.AutoWire;
import net.cassite.byteioc.annoreader.annotations.WireType;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * handler for @AutoWire
 *
 * @see net.cassite.byteioc.annoreader.annotations.AutoWire
 */
public class AutoWireTypeHandler implements TypeAnnotationHandler {

        private static Map<CtClass, WireType> classesToWire = new HashMap<CtClass, WireType>();

        public static Map<CtClass, WireType> getClassesToWire() {
                return classesToWire;
        }

        @Override
        public String handle(String name, CtClass aClass, Collection<Annotation> annotations, TypeChainHandler chain, Helper helper) throws Exception {
                String nameFromChain = chain.handle(name, aClass, annotations, chain, helper);
                AutoWire autoWire = Helper.getAnnotation(AutoWire.class, annotations);
                assert autoWire != null;
                classesToWire.put(aClass, autoWire.value());
                return nameFromChain;
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return Helper.containsAnnotation(AutoWire.class, annotations);
        }
}
