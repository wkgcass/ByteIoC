package net.cassite.byteioc.annoreader.handlers;

import javassist.CtConstructor;
import javassist.CtMethod;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.ParamAnnotationHandler;
import net.cassite.byteioc.annoreader.ParamChainHandler;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * default handler for params
 */
public class ParamHandler implements ParamAnnotationHandler {
        @Override
        public String handleConstructor(String name, int index, CtConstructor member, Collection<Annotation> annotations, ParamChainHandler chain, Helper helper) throws Exception {
                String nameFromChain = chain.handleConstructor(name, index, member, annotations, chain, helper);
                if (!Helper.resultChanged(name, nameFromChain) && name == null) {
                        nameFromChain = helper.getInstanceNameByClass(member.getParameterTypes()[index].getName());
                }
                return nameFromChain;
        }

        @Override
        public String handleMethod(String name, int index, CtMethod member, Collection<Annotation> annotations, ParamChainHandler chain, Helper helper) throws Exception {
                String nameFromChain = chain.handleMethod(name, index, member, annotations, chain, helper);
                if (!Helper.resultChanged(name, nameFromChain) && name == null) {
                        nameFromChain = helper.getInstanceNameByClass(member.getParameterTypes()[index].getName());
                }
                return nameFromChain;
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return true;
        }
}
