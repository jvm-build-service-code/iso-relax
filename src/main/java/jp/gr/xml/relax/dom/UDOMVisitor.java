package jp.gr.xml.relax.dom;

import org.w3c.dom.*;

// Referenced classes of package jp.gr.xml.relax.dom:
//            DOMVisitorException, IDOMVisitor

public final class UDOMVisitor {

    public UDOMVisitor() {
    }

    public static void traverse(Node node, IDOMVisitor idomvisitor)
        throws DOMVisitorException {
        boolean flag;
        switch(node.getNodeType()) {
        case 1: // '\001'
            flag = idomvisitor.enter((Element)node);
            break;

        case 2: // '\002'
            flag = idomvisitor.enter((Attr)node);
            break;

        case 3: // '\003'
            flag = idomvisitor.enter((Text)node);
            break;

        case 4: // '\004'
            flag = idomvisitor.enter((CDATASection)node);
            break;

        case 5: // '\005'
            flag = idomvisitor.enter((EntityReference)node);
            break;

        case 6: // '\006'
            flag = idomvisitor.enter((Entity)node);
            break;

        case 7: // '\007'
            flag = idomvisitor.enter((ProcessingInstruction)node);
            break;

        case 8: // '\b'
            flag = idomvisitor.enter((Comment)node);
            break;

        case 9: // '\t'
            flag = idomvisitor.enter((Document)node);
            break;

        case 10: // '\n'
            flag = idomvisitor.enter((DocumentType)node);
            break;

        case 11: // '\013'
            flag = idomvisitor.enter((DocumentFragment)node);
            break;

        case 12: // '\f'
            flag = idomvisitor.enter((Notation)node);
            break;

        default:
            flag = idomvisitor.enter(node);
            break;
        }
        if(flag) {
            traverseChildren(node, idomvisitor);
            switch(node.getNodeType()) {
            case 1: // '\001'
                idomvisitor.leave((Element)node);
                break;

            case 2: // '\002'
                idomvisitor.leave((Attr)node);
                break;

            case 3: // '\003'
                idomvisitor.leave((Text)node);
                break;

            case 4: // '\004'
                idomvisitor.leave((CDATASection)node);
                break;

            case 5: // '\005'
                idomvisitor.leave((EntityReference)node);
                break;

            case 6: // '\006'
                idomvisitor.leave((Entity)node);
                break;

            case 7: // '\007'
                idomvisitor.leave((ProcessingInstruction)node);
                break;

            case 8: // '\b'
                idomvisitor.leave((Comment)node);
                break;

            case 9: // '\t'
                idomvisitor.leave((Document)node);
                break;

            case 10: // '\n'
                idomvisitor.leave((DocumentType)node);
                break;

            case 11: // '\013'
                idomvisitor.leave((DocumentFragment)node);
                break;

            case 12: // '\f'
                idomvisitor.leave((Notation)node);
                break;

            default:
                idomvisitor.leave(node);
                break;
            }
        }
    }

    public static void traverseChildren(Node node, IDOMVisitor idomvisitor) {
        NodeList nodelist = node.getChildNodes();
        int i = nodelist.getLength();
        for(int j = 0; j < i; j++)
            traverse(nodelist.item(j), idomvisitor);

    }
}
