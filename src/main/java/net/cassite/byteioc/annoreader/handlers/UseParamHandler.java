package net.cassite.byteioc.annoreader.handlers;

import javassist.CtConstructor;
import javassist.CtMethod;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.ParamAnnotationHandler;
import net.cassite.byteioc.annoreader.ParamChainHandler;
import net.cassite.byteioc.annoreader.annotations.Use;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * handler for annotation @Use
 */
public class UseParamHandler implements ParamAnnotationHandler {
        @Override
        public String handleConstructor(String name, int index, CtConstructor member, Collection<Annotation> annotations, ParamChainHandler chain, Helper helper) throws Exception {
                String nameFromChain = chain.handleConstructor(name, member, annotations, chain, helper);
                if (Helper.resultChanged(name, nameFromChain)) {
                        return nameFromChain;
                }
                Use use = Helper.getAnnotation(Use.class, annotations);
                assert use != null;
                if (!"".equals(use.value())) {
                        return use.value();
                } else if (!"net.cassite.byteioc.annoreader.annotations.Use".equals(use.target())) {
                        return helper.getInstanceNameByClass(use.target());
                } else if (!"*(FE#89yF#($Yn98v".equals(use.val())) {
                        return helper.getPrimitive(member.getParameterTypes()[index], use.val());
                }
                return nameFromChain;
        }

        @Override
        public String handleMethod(String name, int index, CtMethod member, Collection<Annotation> annotations, ParamChainHandler chain, Helper helper) throws Exception {
                String nameFromChain = chain.handleMethod(name, member, annotations, chain, helper);
                if (Helper.resultChanged(name, nameFromChain)) {
                        return nameFromChain;
                }
                Use use = Helper.getAnnotation(Use.class, annotations);
                assert use != null;
                if (!"".equals(use.value())) {
                        return use.value();
                } else if (!"net.cassite.byteioc.annoreader.annotations.Use".equals(use.target())) {
                        return helper.getInstanceNameByClass(use.target());
                } else if (!"*(FE#89yF#($Yn98v".equals(use.val())) {
                        return helper.getPrimitive(member.getParameterTypes()[index], use.val());
                }
                return nameFromChain;
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return Helper.containsAnnotation(Use.class, annotations);
        }
}
