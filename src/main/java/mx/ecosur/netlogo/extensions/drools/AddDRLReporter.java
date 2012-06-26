package mx.ecosur.netlogo.extensions.drools;

import org.drools.KnowledgeBase;
import org.drools.builder.*;
import org.drools.io.ResourceFactory;
import org.nlogo.api.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Adds a DRL to the passed in knowledge base and returns
 * the new updated kbase.
 *
 * Usage:
 *
 * drools:add-drl knowledgeBaseRef "path/to/file.drl"
 */
public class AddDRLReporter extends DefaultReporter {

    @Override
    public Syntax getSyntax() {
        return Syntax.reporterSyntax(new int[] {Syntax.WildcardType(),  Syntax.StringType()}, Syntax.WildcardType());
    }

    @Override
    public Object report(Argument[] arguments, Context context) throws ExtensionException, LogoException {
        KnowledgeBase kb = (KnowledgeBase) arguments [0].get();
        String file = arguments[1].getString();
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(kb);

        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new ExtensionException(e);
        }

        kbuilder.add(ResourceFactory.newInputStreamResource(fis), ResourceType.DRL);
        if (kbuilder.hasErrors()) {
            KnowledgeBuilderErrors errors = kbuilder.getErrors();
            StringBuffer es = new StringBuffer();
            for (KnowledgeBuilderError e : errors) {
                es.append(e.getMessage());
            }

            throw new RuntimeException("Errors found while working with changeset: " + es.toString());
        }
        return kbuilder.newKnowledgeBase();
    }

}
