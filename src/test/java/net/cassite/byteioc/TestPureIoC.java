package net.cassite.byteioc;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import net.cassite.byteioc.bytecode.DefaultClassPoolProvider;
import net.cassite.byteioc.bytecode.JavassistByteCodeProcessor;
import net.cassite.byteioc.dependencies.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * test cases
 */
public class TestPureIoC {

        private static JavassistByteCodeProcessor processor;

        @BeforeClass
        public static void classSetUp() {
                processor = new JavassistByteCodeProcessor(new DefaultClassPoolProvider());
        }

        @Test
        public void testFieldMethodInfoNormal() throws Exception {
                FieldMethodInfo fieldMethodInfo = new FieldMethodInfo(new BClass("net.cassite.byteioc.Bean1"));
                fieldMethodInfo.addFieldArg(new BField("testStr", new BClass("java.lang.String")), "$builtin2");
                fieldMethodInfo.addMethodArgs(new BMethod("setTestStr", new BClass[]{new BClass("java.lang.String")}), new String[]{"$builtin3"});

                Assert.assertEquals(new BClass(Bean1.class.getName()), fieldMethodInfo.getCls());
                Assert.assertEquals("$builtin2", fieldMethodInfo.getFieldArgs().get(new BField("testStr", new BClass("java.lang.String"))));
                Assert.assertArrayEquals(new String[]{"$builtin3"}, fieldMethodInfo.getMethodArgs().get(new BMethod("setTestStr", new BClass[]{new BClass("java.lang.String")})));
        }

        @Test
        public void testConstructorInfoNormal() throws Exception {
                ConstructorInfo constructorInfo = new ConstructorInfo(new BClass("net.cassite.byteioc.Bean1"), new BConstructor(new BClass[]{new BClass("java.lang.String")}), new String[]{"$builtin4"});

                Assert.assertEquals(new BClass("net.cassite.byteioc.Bean1"), constructorInfo.getCls());
                Assert.assertEquals(new BConstructor(new BClass[]{new BClass("java.lang.String")}), constructorInfo.getConstructor());
                Assert.assertArrayEquals(new String[]{"$builtin4"}, constructorInfo.getArgs());
        }

        @Test
        public void testPrimitiveInfo() {
                PrimitiveInfo intP = new PrimitiveInfo(1);
                Assert.assertEquals(Integer.class, intP.get().getClass());
                Assert.assertEquals(1, intP.get());

                PrimitiveInfo boolP = new PrimitiveInfo(true);
                Assert.assertEquals(Boolean.class, boolP.get().getClass());
                Assert.assertEquals(true, boolP.get());

                PrimitiveInfo charP = new PrimitiveInfo('a');
                Assert.assertEquals(Character.class, charP.get().getClass());
                Assert.assertEquals('a', charP.get());

                PrimitiveInfo longP = new PrimitiveInfo(1L);
                Assert.assertEquals(Long.class, longP.get().getClass());
                Assert.assertEquals(1L, longP.get());

                PrimitiveInfo shortP = new PrimitiveInfo((short) 1);
                Assert.assertEquals(Short.class, shortP.get().getClass());
                Assert.assertEquals((short) 1, shortP.get());

                PrimitiveInfo floatP = new PrimitiveInfo(1f);
                Assert.assertEquals(Float.class, floatP.get().getClass());
                Assert.assertEquals(1f, floatP.get());

                PrimitiveInfo doubleP = new PrimitiveInfo(1.0);
                Assert.assertEquals(Double.class, doubleP.get().getClass());
                Assert.assertEquals(1.0, doubleP.get());

                PrimitiveInfo byteP = new PrimitiveInfo((byte) 1);
                Assert.assertEquals(Byte.class, byteP.get().getClass());
                Assert.assertEquals((byte) 1, byteP.get());

                PrimitiveInfo strP = new PrimitiveInfo("1");
                Assert.assertEquals(String.class, strP.get().getClass());
                Assert.assertEquals("1", strP.get());
        }

        @Test
        public void testPrimitive() {
                PrimitiveInfo info = new PrimitiveInfo(1);
                Assert.assertEquals("1", processor.generatePrimitive(info));

                info = new PrimitiveInfo(1.0);
                Assert.assertEquals("1.0", processor.generatePrimitive(info));

                info = new PrimitiveInfo(1.0f);
                Assert.assertEquals("1.0f", processor.generatePrimitive(info));

                info = new PrimitiveInfo(1L);
                Assert.assertEquals("1L", processor.generatePrimitive(info));

                info = new PrimitiveInfo((short) 1);
                Assert.assertEquals("(short)1", processor.generatePrimitive(info));

                info = new PrimitiveInfo(true);
                Assert.assertEquals("true", processor.generatePrimitive(info));

                info = new PrimitiveInfo((byte) 1);
                Assert.assertEquals("(byte)1", processor.generatePrimitive(info));

                info = new PrimitiveInfo('a');
                Assert.assertEquals("'a'", processor.generatePrimitive(info));

                info = new PrimitiveInfo("x");
                Assert.assertEquals("\"x\"", processor.generatePrimitive(info));
        }

        @Test
        public void testProcessor() throws Exception {
                Dependencies dependencies = new Dependencies();
                PrimitiveInfo test = new PrimitiveInfo("中文");
                dependencies.addPrimitiveInfo("constant$Test", test);

                PrimitiveInfo test1 = new PrimitiveInfo(1);
                dependencies.addPrimitiveInfo("constant$1", test1);

                // primitive through constructor
                ConstructorInfo info = new ConstructorInfo(new BClass("net.cassite.byteioc.Bean1"), new BConstructor(new BClass[]{new BClass("java.lang.String")}), new String[]{"constant$Test"});
                dependencies.addConstructorInfo("con1", info);
                Assert.assertEquals("new net.cassite.byteioc.Bean1(\"中文\")", processor.instantiateClass("con1", dependencies));

                // primitive through setter
                FieldMethodInfo fieldMethodInfo = new FieldMethodInfo(new BClass("net.cassite.byteioc.Bean1"));
                fieldMethodInfo.addMethodArgs(new BMethod("setI", new BClass[]{new BClass("int")}), new String[]{"constant$1"});
                dependencies.addFieldMethodInfo(fieldMethodInfo);

                // object through setter
                FieldMethodInfo fieldMethodInfo4Bean2 = new FieldMethodInfo(new BClass("net.cassite.byteioc.Bean2"));
                fieldMethodInfo4Bean2.addMethodArgs(new BMethod("setBean1", new BClass[]{new BClass("net.cassite.byteioc.Bean1")}), new String[]{"con1"});
                dependencies.addFieldMethodInfo(fieldMethodInfo4Bean2);

                // constructor with no arg
                ConstructorInfo info4Bean2 = new ConstructorInfo(new BClass("net.cassite.byteioc.Bean2"), new BConstructor(new BClass[]{}), new String[]{});
                dependencies.addConstructorInfo("con2", info4Bean2);

                FieldMethodInfo fieldMethodInfo4Bean3 = new FieldMethodInfo(new BClass("net.cassite.byteioc.Bean3"));
                // object with another object
                fieldMethodInfo4Bean3.addMethodArgs(new BMethod("setBean2", new BClass[]{new BClass("net.cassite.byteioc.Bean2")}), new String[]{"con2"});
                dependencies.addFieldMethodInfo(fieldMethodInfo4Bean3);
                // object through constructor
                ConstructorInfo info4Bean3 = new ConstructorInfo(new BClass("net.cassite.byteioc.Bean3"), new BConstructor(new BClass[]{new BClass("net.cassite.byteioc.Bean1")}), new String[]{"con1"});
                dependencies.addConstructorInfo("con3", info4Bean3);
                // singleton
                dependencies.addSingletonClass(new BClass("net.cassite.byteioc.Bean3"), "con3");

                FieldMethodInfo fieldMethodInfo4Bean4 = new FieldMethodInfo(new BClass("net.cassite.byteioc.Bean4"));
                fieldMethodInfo4Bean4.addMethodArgs(new BMethod("setBean3", new BClass[]{new BClass("net.cassite.byteioc.Bean3")}), new String[]{"con3"});
                // multiple set
                fieldMethodInfo4Bean4.addMethodArgs(new BMethod("setBean1", new BClass[]{new BClass("net.cassite.byteioc.Bean1")}), new String[]{"con1"});
                // generator
                fieldMethodInfo4Bean4.addMethodArgs(new BMethod("setBean2", new BClass[]{new BClass("net.cassite.byteioc.Bean2")}), new String[]{"gen1"});
                // field
                fieldMethodInfo4Bean4.addFieldArg(new BField("testStr", new BClass("java.lang.String")), "constant$Test2");
                dependencies.addFieldMethodInfo(fieldMethodInfo4Bean4);

                ConstructorInfo genCon = new ConstructorInfo(new BClass("net.cassite.byteioc.PlainGenerator"), new BConstructor(new BClass[]{}), new String[]{});
                dependencies.addConstructorInfo("gen1con", genCon);
                dependencies.addGenerator("gen1", "gen1con");

                PrimitiveInfo primitiveInfo = new PrimitiveInfo("abcdabcd");
                dependencies.addPrimitiveInfo("constant$Test2", primitiveInfo);

                processor.process(dependencies);

                Bean1 bean1 = new Bean1("xx");
                Assert.assertEquals(1, bean1.getI());

                Bean2 bean2 = new Bean2();
                Assert.assertEquals(Bean1.class, bean2.getBean1().getClass());
                Assert.assertEquals("中文", bean2.getBean1().getTestStr());

                Bean4 bean4 = new Bean4();
                Assert.assertEquals(Bean3.class, bean4.getBean3().getClass());
                Assert.assertEquals(Bean1.class, bean4.getBean3().getBean1().getClass());
                Assert.assertEquals(1, bean4.getBean3().getBean1().getI());
                Assert.assertEquals(1, bean4.getBean3().getBean2().getBean1().getI());
                Assert.assertNotNull(bean4.getBean1());
                Assert.assertNotNull(bean4.getBean2());
                Assert.assertEquals("abcdabcd", bean4.getTestStr());

                Bean4 bean4_2 = new Bean4();
                Assert.assertNotEquals(bean4, bean4_2);
                Assert.assertEquals(bean4.getBean3(), bean4_2.getBean3());

                try {
                        new Bean3(new Bean1("x"));
                } catch (Throwable t) {
                        Assert.assertEquals(IllegalAccessError.class, t.getClass());
                }
        }
}
