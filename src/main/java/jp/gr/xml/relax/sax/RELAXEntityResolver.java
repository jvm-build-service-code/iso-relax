package jp.gr.xml.relax.sax;

import java.net.URL;

// Referenced classes of package jp.gr.xml.relax.sax:
//            SimpleEntityResolver

public class RELAXEntityResolver extends SimpleEntityResolver {

    public RELAXEntityResolver() {
        String s = getClass().getResource("/jp/gr/xml/relax/lib/relaxCore.dtd").toExternalForm();
        String s1 = getClass().getResource("/jp/gr/xml/relax/lib/relaxNamespace.dtd").toExternalForm();
        String s2 = getClass().getResource("/jp/gr/xml/relax/lib/relax.dtd").toExternalForm();
        addSystemId("http://www.xml.gr.jp/relax/core1/relaxCore.dtd", s);
        addSystemId("relaxCore.dtd", s);
        addSystemId("relaxNamespace.dtd", s1);
        addSystemId("relax.dtd", s2);
        addPublicId("-//RELAX//DTD RELAX Core 1.0//JA", s);
        addPublicId("-//RELAX//DTD RELAX Namespace 1.0//JA", s1);
        addPublicId("-//RELAX//DTD RELAX Grammar 1.0//JA", s2);
    }
}
