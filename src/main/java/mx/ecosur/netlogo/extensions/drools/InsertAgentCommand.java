package mx.ecosur.netlogo.extensions.drools;

import org.drools.runtime.KnowledgeRuntime;
import org.drools.runtime.rule.FactHandle;
import org.nlogo.api.*;

/**
 * Created with IntelliJ IDEA.
 * User: awaterma
 * Date: 5/25/12
 * Time: 1:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class InsertAgentCommand extends InsertCommand {

    @Override
    public Syntax getSyntax() {
        return Syntax.commandSyntax(new int[] {Syntax.WildcardType(), Syntax.WildcardType()});
    }

    @Override
    public void perform(Argument[] arguments, Context context) throws ExtensionException, LogoException {
        KnowledgeRuntime runtime = (KnowledgeRuntime) arguments [ 0 ].get();
        Agent a = arguments [ 1 ].getAgent();
        runtime.insert(a);
        /* ensures only the last inserted context is active in working memory */
        insertContextSingleton(runtime , context);
    }

}
