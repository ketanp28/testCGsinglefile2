
package blackberry.push;

import net.rim.device.api.script.Scriptable;
import net.rim.device.api.script.ScriptableFunction;
import blackberry.core.FunctionSignature;
import blackberry.core.ScriptableFunctionBase;


public class OpenBESPushListenerFunction extends ScriptableFunctionBase {

    private static final String KEY_PORT = "port";
    private static final String KEY_WAKEUP_PAGE = "wakeUpPage";
    private static final String KEY_MAX_QUEUE_CAP = "maxQueueCap";


    protected Object execute( Object thiz, Object[] args ) throws Exception {
        Scriptable obj = (Scriptable) args[ 0 ];
        int port = ( (Integer) obj.getField( KEY_PORT ) ).intValue();
        String wakeUpPage = obj.getField( KEY_WAKEUP_PAGE ).toString();
        int maxQueueCap = 0;
        Object maxQueueCapObj = obj.getField( KEY_MAX_QUEUE_CAP );
        
        ScriptableFunction onData = (ScriptableFunction) args[ 1 ];
        ScriptableFunction onSimChange = (ScriptableFunction) args[ 2 ];
        PushService.getInstance().openBESPushChannel( port, wakeUpPage, maxQueueCap, onData, onSimChange );
        return UNDEFINED;
    }


    protected void validateArgs( Object[] args ) {
        super.validateArgs( args );

        Scriptable obj = (Scriptable) args[ 0 ];
        try {
            Object port = obj.getField( KEY_PORT );
            if( port != null && port != UNDEFINED ) {
                int portValue = ( (Integer) port ).intValue();
                if( portValue < 0 ) {
                    throw new IllegalArgumentException( "Invalid port." );
                } else if( !PushService.isValidPort( portValue ) ) {
                    throw new IllegalArgumentException( "Reserved port" );
                }
            } else {
                throw new IllegalArgumentException( "Port is missing." );
            }
            Object wakeUpPage = obj.getField( KEY_WAKEUP_PAGE );
            
        } catch( Exception e ) {
            throw new IllegalArgumentException( "Error retrieving arguments: " + e.getMessage() );
        }
    }


    protected FunctionSignature[] getFunctionSignatures() {
        FunctionSignature fs = new FunctionSignature( 3 );
        fs.addParam( Object.class, true );
        fs.addParam( ScriptableFunction.class, true );
        return new FunctionSignature[] { fs };
    }

}
