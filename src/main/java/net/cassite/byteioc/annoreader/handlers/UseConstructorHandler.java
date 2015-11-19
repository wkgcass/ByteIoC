package net.cassite.byteioc.annoreader.handlers;

import javassist.CtClass;
import javassist.CtConstructor;
import net.cassite.byteioc.annoreader.ConstructorAnnotationHandler;
import net.cassite.byteioc.annoreader.ConstructorChainHandler;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.annotations.Use;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * handler for annotation use
 */
public class UseConstructorHandler implements ConstructorAnnotationHandler {
        @Override
        public String handle(String name, String[] args, CtConstructor constructor, Collection<Annotation> annotations, ConstructorChainHandler chain, Helper helper) throws Exception {
                String[] argsBackup = Helper.argsBackup(args);
                String nameFromChain = chain.handle(name, args, constructor, annotations, chain, helper);
                if (!Helper.resultChanged(argsBackup, args) && constructor.getParameterTypes().length == 1) {
                        if (args == null) {
                                args = new String[1];
                        }
                        Use use = Helper.getAnnotation(Use.class, annotations);
                        assert use != null;
                        if (!"".equals(use.value())) {
                                args[0] = use.value();
                        } else if (!"net.cassite.byteioc.annoreader.annotations.Use".equals(use.target())) {
                                args[0] = helper.getInstanceNameByClass(use.target());
                        } else if (!"*(FE#89yF#($Yn98v".equals(use.val())) {
                                CtClass paramCls = constructor.getParameterTypes()[0];
                                args[0] = helper.getPrimitive(paramCls, use.val());
                        }
                }
                return nameFromChain;
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return Helper.containsAnnotation(Use.class, annotations);
        }
}
