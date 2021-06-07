/**
 * This source code is distributed under the CECILL v2.1 license
 * https://opensource.org/licenses/CECILL-2.1
 * 
 * It gives a simple example of authentication in Java on Microsoft Office 365 (Azure AD), 
 * by entering just the necessary information such as the client ID of the registration 
 * of this application with Microsoft, the username and password of the user. 
 * The result, when the username / password pair is correct, is all the information 
 * made available by Microsoft according to the rights assigned by the Azure 
 * Cloud administrator (see scope)
 * 
 * @author Serge COUDÉ
 * @url http://www.capitchilog.fr/
 * @date 2021-06
 * @version 1.0
 */

package jazadauthentication;

/*
 * All the classes used in this program
 */
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;
import java.util.Properties;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.microsoft.graph.models.User;  
// https://javadoc.io/static/com.microsoft.graph/microsoft-graph/3.5.0/com/microsoft/graph/models/User.html
// https://jar-download.com/artifacts/com.microsoft.graph
import com.microsoft.graph.authentication.TokenCredentialAuthProvider; 
import com.microsoft.graph.requests.GraphServiceClient;
// https://javadoc.io/static/com.microsoft.graph/microsoft-graph/3.7.0/com/microsoft/graph/requests/GraphServiceClient.html
import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.microsoft.graph.models.DirectoryObject;
import com.microsoft.graph.models.Group;
import com.microsoft.graph.requests.DirectoryObjectCollectionWithReferencesPage;
import com.microsoft.graph.requests.DirectoryObjectCollectionWithReferencesRequestBuilder;
import com.microsoft.graph.requests.UserRequestBuilder;
import java.io.InputStream;
//https://jar-download.com/artifacts/com.azure/azure-identity


/**
 * JAR dependecies (maybe too much but it work !
|  	annotations-13.0.jar
|  	azure-core-1.16.0.jar
|  	checker-qual-3.8.0.jar
|  	error_prone_annotations-2.5.1.jar
|  	failureaccess-1.0.1.jar
|  	gson-2.8.7.jar
|  	guava-30.1.1-jre.jar
|  	j2objc-annotations-1.3.jar
|  	jackson-annotations-2.12.2.jar
|  	jackson-core-2.12.2.jar
|  	jackson-databind-2.12.2.jar
|  	jackson-dataformat-xml-2.12.2.jar
|  	jackson-datatype-jsr310-2.12.2.jar
|  	jackson-module-jaxb-annotations-2.12.2.jar
|  	jakarta.activation-api-1.2.1.jar
|  	jakarta.xml.bind-api-2.3.2.jar
|  	jsr305-3.0.2.jar
|  	kotlin-stdlib-1.4.10.jar
|  	kotlin-stdlib-common-1.4.0.jar
|  	listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar
|  	microsoft-graph-3.7.0.jar
|  	microsoft-graph-core-2.0.3.jar
|  	netty-tcnative-boringssl-static-2.0.38.Final.jar
|  	okhttp-4.9.1.jar
|  	okio-2.8.0.jar
|  	reactive-streams-1.0.3.jar
|  	reactor-core-3.4.5.jar
|  	slf4j-api-1.7.30.jar
|  	stax2-api-4.2.1.jar
|  	woodstox-core-6.2.4.jar
|  	KeePassJava2-2.1.4.jar
|  	KeePassJava2-dom-2.1.4.jar
|  	KeePassJava2-jaxb-2.1.4.jar
|  	KeePassJava2-kdb-2.1.4.jar
|  	KeePassJava2-kdbx-2.1.4.jar
|  	KeePassJava2-simple-2.1.4.jar
|  	aalto-xml-1.0.0.jar
|  	accessors-smart-2.4.2.jar
|  	annotations-15.0.jar
|  	asm-8.0.1.jar
|  	azure-core-http-netty-1.9.2.jar
|  	azure-identity-1.3.0.jar
|  	brave-5.13.3.jar
|  	brave-instrumentation-http-5.13.3.jar
|  	commons-codec-1.10.jar
|  	content-type-2.1.jar
|  	core-1.54.0.0.jar
|  	database-2.1.4.jar
|  	guava-19.0.jar
|  	httpcore-4.4.5.jar
|  	jcip-annotations-1.0-1.jar
|  	jna-5.5.0.jar
|  	jna-platform-5.6.0.jar
|  	json-smart-2.4.2.jar
|  	lang-tag-1.5.jar
|  	msal4j-1.10.0.jar
|  	msal4j-persistence-extension-1.1.0.jar
|  	netty-buffer-4.1.63.Final.jar
|  	netty-codec-4.1.63.Final.jar
|  	netty-codec-dns-4.1.63.Final.jar
|  	netty-codec-http-4.1.63.Final.jar
|  	netty-codec-http2-4.1.63.Final.jar
|  	netty-codec-socks-4.1.63.Final.jar
|  	netty-common-4.1.63.Final.jar
|  	netty-handler-4.1.63.Final.jar
|  	netty-handler-proxy-4.1.63.Final.jar
|  	netty-resolver-4.1.63.Final.jar
|  	netty-resolver-dns-4.1.63.Final.jar
|  	netty-resolver-dns-native-macos-4.1.63.Final-osx-x86_64.jar
|  	netty-transport-4.1.63.Final.jar
|  	netty-transport-native-epoll-4.1.63.Final-linux-x86_64.jar
|  	netty-transport-native-kqueue-4.1.63.Final-osx-x86_64.jar
|  	netty-transport-native-unix-common-4.1.63.Final.jar
|  	nimbus-jose-jwt-9.8.1.jar
|  	oauth2-oidc-sdk-9.4.jar
|  	reactor-netty-1.0.6.jar
|  	reactor-netty-core-1.0.6.jar
|  	reactor-netty-http-1.0.6.jar
|  	reactor-netty-http-brave-1.0.6.jar
|  	simple-xml-2.7.1.jar
|  	stax-1.2.0.jar
|  	stax-api-1.0.1.jar
|  	xpp3-1.1.3.3.jar
|  	zipkin-2.23.2.jar
|  	zipkin-reporter-2.16.3.jar
|  	zipkin-reporter-brave-2.16.3.jar
|  	log4j-api-2.14.1.jar
|  	log4j-core-2.14.1.jar
|  	log4j-slf4j-impl-2.14.1.jar
 */


/**
 * Main class for the app
 * @author Serge COUDÉ
 */
public class JAZADAuthentication {

    /**
     * @var String _configFile Azure App configuration file
     */
    private String _configFile = "jazadauthentication/config.properties";
    /**
     * @var String _clientID ID for Azure client App 
     */
    private String _clientID;
    /**
     * @var String _authority authority URL
     */
    private String _authority;
    /**
     * @var String[] _scodeAD scope of Azure App used
     */
    private Set<String> _scopeAD;
    /**
     * @var String _userName Office365 username of user
     */
    private String _userName;
    /**
     * @var String _password Office365 password of user
     */
    private String _password;
    
    /**
     * @var Logger log Log Object for JAZADAuthentication App (use SLF4J/Log4J2 and log4j2.xml config file in the root of project by default)
     */
    public static Logger log = LoggerFactory.getLogger(JAZADAuthentication.class);

    /**
     * JAZADAuthentication
     * 
     * App constructor
     */
    public JAZADAuthentication() {
        try {
            loadConfiguration();
            // Initialisation of user id and password
            this._userName = "myemail@provider.com";
            this._password = "mypassword";
            log.info("Authentication for " + this._userName);
            // Get an object to query the AD 
            UserRequestBuilder urb = this.authenticateUser();
            // Get User object from id and passord
            User me = urb.buildRequest().get();
            if (me != null) {
                log.info("Compagnie : " + me.companyName);
                log.info("aboutMe : " + me.aboutMe);
                log.info("accountEnabled : " + me.accountEnabled);
                log.info("ageGroup : " + me.ageGroup);
                log.info("city : " + me.city);
                log.info("country : " + me.country);
                log.info("createdDateTime : " + me.createdDateTime);
                log.info("department : " + me.department);
                log.info("displayName : " + me.displayName);
                log.info("employeeId : " + me.employeeId);
                log.info("employeeType : " + me.employeeType);
                log.info("events : " + me.events);
                log.info("externalUserState : " + me.externalUserState);
                log.info("givenName : " + me.givenName);
                log.info("jobTitle : " + me.jobTitle);
                log.info("lastPasswordChangeDateTime : " + me.lastPasswordChangeDateTime);
                log.info("legalAgeGroupClassification : " + me.legalAgeGroupClassification);
                log.info("memberOf : " + me.memberOf);
                log.info("mySite : " + me.mySite);
                log.info("officeLocation : " + me.officeLocation);
                log.info("schools : " + me.schools);
                log.info("state : " + me.state);
                log.info("surname : " + me.surname);
                log.info("userPrincipalName : " + me.userPrincipalName);
                log.info("userType : " + me.userType);
                // Get object to have the groups to which the user is registered
                DirectoryObjectCollectionWithReferencesPage collection = urb.memberOf().buildRequest().get();
                log.info("Groups member :");
                // There is a pagination system
                while (collection != null) {
                    List<DirectoryObject> directoryObjects = collection.getCurrentPage();
                    for (DirectoryObject item: directoryObjects) {
                        // all items are not necessary a group, we have to test
                        if (item instanceof Group) {
                            Group g = (Group)item;
                            log.info("\t" + g.displayName);
                        }
                    }
                    DirectoryObjectCollectionWithReferencesRequestBuilder nextPage = collection.getNextPage();
                    if (nextPage == null) {
                        // no more page, that's all falk !
                        break;
                    } else {
                        // next page
                        collection = nextPage.buildRequest().get();
                    }
                }
            } else {
                log.warn("login/password invalid");
            }   
        } catch(FileNotFoundException fnfe) {
            log.error(fnfe.toString());
        } catch(IOException ioe) {
            log.error(ioe.toString());
        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Get object to query the AD 
     * 
     * @return UserRequestBuilder Query builder for AD
     */
    public UserRequestBuilder authenticateUser() {
        
        // Prepare the query with application client id, user username and his password
        UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredentialBuilder()
                    .clientId(this._clientID)
                    .username(this._userName)
                    .password(this._password)
                    .build();

        // Get token from scope and all authentication information
        // The scope is important, depending on its scope (list of items provided),
        // the information obtained will not be as complete as hoped!
        // But maybe you won't have all the possible rights ...
        TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider((List<String>)new ArrayList<>(this._scopeAD), usernamePasswordCredential);

        // Object to return
        GraphServiceClient graphClient =
              GraphServiceClient
                .builder()
                .authenticationProvider(tokenCredentialAuthProvider)
                .buildClient();

        return graphClient.me();       
    }

    /**
     * Load the configuration data for Microsoft Office365 Azure AD API
     * and hydrate the necessary fields
     * 
     * @throws FileNotFoundException File is not present
     * @throws IOException Problem while reading the file
     */
    private void loadConfiguration() throws FileNotFoundException, IOException {
        // Properties object creation, empty
        Properties properties = new Properties();
        // Get file stream of config.properties file
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(this._configFile);
        if (inputStream != null) {
            // stream is present, we load his content
            properties.load(inputStream);
	} else {
            // no stream, error...
            throw new FileNotFoundException("property file '" + this._configFile + "' not found in the classpath");
	}
        // Hydrating the fields
        this._authority = properties.getProperty("AUTHORITY");
        this._scopeAD = Collections.singleton(properties.getProperty("SCOPE"));
        this._clientID = properties.getProperty("CLIENT_ID");
    }

    /**
     * Start of the application
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JAZADAuthentication app = new JAZADAuthentication();
    }

}
