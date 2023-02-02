package jp.gr.xml.relax.sax;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class SimpleEntityResolver
    implements EntityResolver {

    public SimpleEntityResolver() {
        publicIds_ = new HashMap();
        systemIds_ = new HashMap();
        relativeSystemIds_ = new ArrayList();
    }

    public SimpleEntityResolver(String s, String s1) {
        publicIds_ = new HashMap();
        systemIds_ = new HashMap();
        relativeSystemIds_ = new ArrayList();
        _init(new String[][] {
            new String[] {
                s, s1
            }
        }, null);
    }

    public SimpleEntityResolver(String as[][]) {
        publicIds_ = new HashMap();
        systemIds_ = new HashMap();
        relativeSystemIds_ = new ArrayList();
        _init(as, null);
    }

    public SimpleEntityResolver(String as[][], String as1[][]) {
        publicIds_ = new HashMap();
        systemIds_ = new HashMap();
        relativeSystemIds_ = new ArrayList();
        _init(as, as1);
    }

    private void _init(String as[][], String as1[][]) {
        if(as != null) {
            ArrayList arraylist = new ArrayList();
            for(int j = 0; j < as.length; j++) {
                String s = as[j][0];
                addSystemId(s, as[j][1]);
            }

        }
        if(as1 != null) {
            for(int i = 0; i < as1.length; i++)
                addPublicId(as1[i][0], as1[i][1]);

        }
    }

    public void addSystemId(String s, String s1) {
        systemIds_.put(s, s1);
        relativeSystemIds_.add(s);
    }

    public void addPublicId(String s, String s1) {
        publicIds_.put(s, s1);
    }

    public InputSource resolveEntity(String s, String s1) {
        if(s1 != null && _isExist(s1))
            return new InputSource(s1);
        if(s != null) {
            String s2 = (String)publicIds_.get(s);
            if(s2 != null)
                return new InputSource(s2);
            else
                return null;
        }
        if(s1 != null) {
            String s3 = _getURIBySystemId(s1);
            if(s3 != null)
                return new InputSource(s3);
            else
                return new InputSource(s1);
        } else {
            return null;
        }
    }

    private boolean _isExist(String s) {
        try {
            URL url = new URL(s);
            if("file".equals(url.getProtocol())) {
                InputStream inputstream = url.openStream();
                inputstream.close();
                return true;
            } else {
                return false;
            }
        }
        catch(IOException ioexception) {
            return false;
        }
    }

    private String _getURIBySystemId(String s) {
        String s1 = (String)systemIds_.get(s);
        if(s1 != null)
            return s1;
        int i = relativeSystemIds_.size();
        for(int j = 0; j < i; j++) {
            String s2 = (String)relativeSystemIds_.get(j);
            if(s.endsWith(s2))
                return (String)systemIds_.get(s2);
        }

        return null;
    }

    private Map publicIds_;
    private Map systemIds_;
    private List relativeSystemIds_;
}
