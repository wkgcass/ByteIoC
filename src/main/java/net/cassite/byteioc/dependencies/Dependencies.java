package net.cassite.byteioc.dependencies;

import net.cassite.byteioc.exceptions.DuplicateClassException;
import net.cassite.byteioc.exceptions.DuplicateNameException;

import java.util.*;

/**
 * dependencies mapped with constructorsInfo and fieldMethodInfo
 */
public class Dependencies {
        /**
         * singleton classes' names =$gt; constructor name
         */
        private final Map<BClass, String> singletonClasses = new HashMap<BClass, String>();
        /**
         * constructor info<br>
         * including which constructor to use, what param to fill
         */
        private final Map<String, ConstructorInfo> constructorInfoMap = new HashMap<String, ConstructorInfo>();
        /**
         * primitive info<br>
         * including 8 primitives and String (generally speaking, those values can be directly used in java language without using <b>new</b>)
         */
        private final Map<String, PrimitiveInfo> primitiveInfoMap = new HashMap<String, PrimitiveInfo>();
        /**
         * field and method info<br>
         * field ,method and their arguments.
         */
        private final Map<BClass, FieldMethodInfo> fieldMethodInfoMap = new HashMap<BClass, FieldMethodInfo>();
        /**
         * generator name =&gt; generator name
         */
        private final Map<String, String> generatorMap = new HashMap<String, String>();

        private void validateName(String name) {
                if (constructorInfoMap.containsKey(name) || primitiveInfoMap.containsKey(name) || generatorMap.containsKey(name)) {
                        throw new DuplicateNameException(name);
                }
        }

        public Dependencies addSingletonClass(BClass singletonClass, String constructor) {
                this.singletonClasses.put(singletonClass, constructor);
                return this;
        }

        public Dependencies addConstructorInfo(String name, ConstructorInfo constructorInfo) {
                validateName(name);
                constructorInfoMap.put(name, constructorInfo);
                return this;
        }

        public Dependencies addPrimitiveInfo(String name, PrimitiveInfo primitiveInfo) {
                validateName(name);
                primitiveInfoMap.put(name, primitiveInfo);
                return this;
        }

        public Dependencies addFieldMethodInfo(FieldMethodInfo fieldMethodInfo) {
                if (fieldMethodInfoMap.containsKey(fieldMethodInfo.getCls())) {
                        throw new DuplicateClassException(fieldMethodInfo.getCls().getClassName());
                }
                fieldMethodInfoMap.put(fieldMethodInfo.getCls(), fieldMethodInfo);
                return this;
        }

        public Dependencies addGenerator(String name, String generator) {
                validateName(name);
                generatorMap.put(name, generator);
                return this;
        }

        public Map<BClass, String> getSingletonClasses() {
                return singletonClasses;
        }

        public Map<String, ConstructorInfo> getConstructorInfoMap() {
                return constructorInfoMap;
        }

        public Map<String, PrimitiveInfo> getPrimitiveInfoMap() {
                return primitiveInfoMap;
        }

        public Map<BClass, FieldMethodInfo> getFieldMethodInfoMap() {
                return fieldMethodInfoMap;
        }

        public Map<String, String> getGeneratorMap() {
                return generatorMap;
        }
}
