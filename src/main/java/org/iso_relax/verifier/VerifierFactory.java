package org.iso_relax.verifier;

import java.io.*;
import java.net.URL;
import java.util.*;
import org.xml.sax.*;

// Referenced classes of package org.iso_relax.verifier:
//            VerifierFactoryLoader, VerifierConfigurationException, Schema, Verifier

public abstract class VerifierFactory {

    public VerifierFactory() {
        resolver = null;
    }

    public Verifier newVerifier(String s)
        throws VerifierConfigurationException, SAXException, IOException {
        return compileSchema(s).newVerifier();
    }

    public Verifier newVerifier(File file)
        throws VerifierConfigurationException, SAXException, IOException {
        return compileSchema(file).newVerifier();
    }

    public Verifier newVerifier(InputStream inputstream)
        throws VerifierConfigurationException, SAXException, IOException {
        return compileSchema(inputstream, null).newVerifier();
    }

    public Verifier newVerifier(InputStream inputstream, String s)
        throws VerifierConfigurationException, SAXException, IOException {
        return compileSchema(inputstream, s).newVerifier();
    }

    public Verifier newVerifier(InputSource inputsource)
        throws VerifierConfigurationException, SAXException, IOException {
        return compileSchema(inputsource).newVerifier();
    }

    public abstract Schema compileSchema(InputSource inputsource)
        throws VerifierConfigurationException, SAXException, IOException;

    public Schema compileSchema(String s)
        throws VerifierConfigurationException, SAXException, IOException {
        return compileSchema(new InputSource(s));
    }

    public Schema compileSchema(InputStream inputstream)
        throws VerifierConfigurationException, SAXException, IOException {
        return compileSchema(inputstream, null);
    }

    public Schema compileSchema(InputStream inputstream, String s)
        throws VerifierConfigurationException, SAXException, IOException {
        InputSource inputsource = new InputSource(inputstream);
        inputsource.setSystemId(s);
        return compileSchema(inputsource);
    }

    public Schema compileSchema(File file)
        throws VerifierConfigurationException, SAXException, IOException {
        String s = "file:" + file.getAbsolutePath();
        if(File.separatorChar == '\\')
            s = s.replace('\\', '/');
        return compileSchema(new InputSource(s));
    }

    public boolean isFeature(String s)
        throws SAXNotRecognizedException, SAXNotSupportedException {
        if("http://www.iso-relax.org/verifier/handler".equals(s) || "http://www.iso-relax.org/verifier/filter".equals(s))
            return true;
        else
            throw new SAXNotRecognizedException(s);
    }

    public void setFeature(String s, boolean flag)
        throws SAXNotRecognizedException, SAXNotSupportedException {
        throw new SAXNotRecognizedException(s);
    }

    public Object getProperty(String s)
        throws SAXNotRecognizedException, SAXNotSupportedException {
        throw new SAXNotRecognizedException(s);
    }

    public void setProperty(String s, Object obj)
        throws SAXNotRecognizedException, SAXNotSupportedException {
        throw new SAXNotRecognizedException(s);
    }

    public void setEntityResolver(EntityResolver entityresolver) {
        resolver = entityresolver;
    }

    public EntityResolver getEntityResolver() {
        return resolver;
    }

    /**
     * @deprecated Method newInstance is deprecated
     */

    public static VerifierFactory newInstance()
        throws VerifierConfigurationException {
        return newInstance("http://www.xml.gr.jp/xmlns/relaxNamespace");
    }

    public static VerifierFactory newInstance(String s)
        throws VerifierConfigurationException {
        for(Iterator iterator = providers(org.iso_relax.verifier.VerifierFactoryLoader.class); iterator.hasNext();) {
            VerifierFactoryLoader verifierfactoryloader = (VerifierFactoryLoader)iterator.next();
            try {
                VerifierFactory verifierfactory = verifierfactoryloader.createFactory(s);
                if(verifierfactory != null)
                    return verifierfactory;
            }
            catch(Throwable throwable) { }
        }

        throw new VerifierConfigurationException("no validation engine available for: " + s);
    }

    private static synchronized Iterator providers(Class class1) {
        ClassLoader classloader = class1.getClassLoader();
        String s = "META-INF/services/" + class1.getName();
        Vector vector = (Vector)providerMap.get(s);
        if(vector != null)
            return vector.iterator();
        vector = new Vector();
        providerMap.put(s, vector);
        Enumeration enumeration;
        try {
            enumeration = classloader.getResources(s);
        }
        catch(IOException ioexception) {
            return vector.iterator();
        }
        while(enumeration.hasMoreElements())
            try {
                URL url = (URL)enumeration.nextElement();
                InputStream inputstream = url.openStream();
                InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "UTF-8");
                BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
                String s1 = bufferedreader.readLine();
                while(s1 != null)  {
                    try {
                        int i = s1.indexOf('#');
                        if(i != -1)
                            s1 = s1.substring(0, i);
                        s1 = s1.trim();
                        if(s1.length() == 0) {
                            s1 = bufferedreader.readLine();
                            continue;
                        }
                        Object obj = classloader.loadClass(s1).newInstance();
                        vector.add(obj);
                    }
                    catch(Exception exception1) { }
                    s1 = bufferedreader.readLine();
                }
            }
            catch(Exception exception) { }
        return vector.iterator();
    }

    private EntityResolver resolver;
    private static HashMap providerMap = new HashMap();

}
