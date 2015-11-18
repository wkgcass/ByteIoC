package net.cassite.byteioc.annoreader.handlers;

import javassist.CtClass;
import javassist.CtMethod;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.MethodAnnotationHandler;
import net.cassite.byteioc.annoreader.MethodChainHandler;
import net.cassite.byteioc.annoreader.annotations.Use;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * handles @Use annotation on methods
 */
public class UseMethodHandler implements MethodAnnotationHandler {
        @Override
        public String[] handle(String[] args, CtMethod method, Collection<Annotation> annotations, MethodChainHandler chain, Helper helper) throws Exception {
                String[] argsFromChain = chain.handle(args, method, annotations, chain, helper);
                if (Helper.resultChanged(args, argsFromChain)) {
                        return argsFromChain;
                }
                if (args == null) {
                        args = new String[0];
                }
                Use use = Helper.getAnnotation(Use.class, annotations);
                assert use != null;
                if (!"".equals(use.value())) {
                        args[0] = use.value();
                } else if (!"net.cassite.byteioc.annoreader.annotations.Use".equals(use.target())) {
                        args[0] = helper.getInstanceNameByClass(use.target());
                } else if (!"*(FE#89yF#($Yn98v".equals(use.val())) {
                        CtClass paramCls = method.getParameterTypes()[0];
                        args[0] = helper.getPrimitive(paramCls, use.val());
                }
                return args;
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return Helper.containsAnnotation(Use.class, annotations);
        }
}
