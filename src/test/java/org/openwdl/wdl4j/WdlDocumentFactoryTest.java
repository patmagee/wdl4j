package org.openwdl.wdl4j;

import org.junit.jupiter.api.Test;
import org.openwdl.wdl4j.v1.Document;
import org.openwdl.wdl4j.v1.WdlDocumentFactory;

import static org.junit.jupiter.api.Assertions.*;

class WdlDocumentFactoryTest {


    @Test
    public void test(){
        String wdl = "version 1.0 workflow a { input {Stris p = 'Happy are those ~{1 + 2}'} }";
        String wdl2 = "version 1.0" + "\nstruct foo {" + " File f" + "}" + "\ntask bar {" + " input { foo inp } command <<< echo ~{sep='c' inp.f} >>>" + "}" + "\nworkflow biz {" + " input { foo inp } " + "\n call bar as boz { input: inp = inp }" + "\n}";
        String wdl3 = "version 1.0\n" + "task foo {\n" + "  input {  \n" + "     Int min_std_max_min\n" + "     String prefix\n" + "  }\n" + "  command {\n" + "     echo ${sep=',' min_std_max_min}\n" + "  }\n";
        String wdl4 = "version 1.0\n" + "\n" + "\n" + "#EXPECTED_ERROR line:6 msg:\"Only unbound declarations are allowed\"\n" + "struct FooStruct {\n" + "  Int p = 100\n" + "}\n" + "\n" + "#EXPECTED_ERROR line:10 msg:\"Struct names must be an identifier\"\n" + "struct 8_IllegalName {\n" + "  Int p\n" + "}\n";
//        Document document = WdlDocumentFactory.from(wdl);
        Document document2 = WdlDocumentFactory.from(wdl4);
//        assertEquals("1.0",document.getVersion().getRelease());
        document2.getVersion();
    }

}