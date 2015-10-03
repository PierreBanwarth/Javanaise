/***
 * JAVANAISE Implementation
 * JvnServerImpl class
 * Contact: 
 *
 * Authors: 
 */

package jvn;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.io.Serializable;



public class JvnCoordImpl 	
              extends UnicastRemoteObject 
							implements JvnRemoteCoord{
	
	private State objectsState = State.R;
	private HashMap<JvnRemoteServer , Integer> rmServer_objId_Map;
	private HashMap<Integer , JvnObject> objId_rmObject_Map;
	private HashMap<String , Integer> symbolicName_objId_Map;
	

  /**
  * Default constructor
  * @throws JvnException
  **/
	private JvnCoordImpl() throws Exception {
		// to be completed
		this.objectsState = State.R;
		this.rmServer_objId_Map = new HashMap<JvnRemoteServer , Integer>();
		this.objId_rmObject_Map = new HashMap<Integer , JvnObject>();
		this.symbolicName_objId_Map = new HashMap<String , Integer>();
	}
	
	
	public static void main(String[] args){
		
		try{
			LocateRegistry.createRegistry(5555);
            JvnCoordImpl coordinator = new JvnCoordImpl();
            Naming.rebind("//localhost:5555/COORDINATOR/", coordinator);
            System.out.println("Coordinator Ready!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

  /**
  *  Allocate a NEW JVN object id (usually allocated to a 
  *  newly created JVN object)
  * @throws java.rmi.RemoteException,JvnException
  **/
  public int jvnGetObjectId()
  throws java.rmi.RemoteException,jvn.JvnException {
    // to be completed 
	  int id = 1;
	  while(this.rmServer_objId_Map.containsValue(id))id++;
	  return id;
  }
  
  /**
  * Associate a symbolic name with a JVN object
  * @param jon : the JVN object name
  * @param jo  : the JVN object 
  * @param joi : the JVN object identification
  * @param js  : the remote reference of the JVNServer
  * @throws java.rmi.RemoteException,JvnException
  **/
  public void jvnRegisterObject(String jon, JvnObject jo, JvnRemoteServer js)
  throws java.rmi.RemoteException,jvn.JvnException{
    // to be completed 
		if(this.symbolicName_objId_Map.containsKey(jon)) {
			System.out.println("existing object with symbollic name = " + jon);
			System.out.println("id : " + this.rmServer_objId_Map.get(js));
			System.out.println("rmObj : " + this.objId_rmObject_Map.get(this.rmServer_objId_Map.get(js)));
		}else{
			System.out.println("No object with symbollic name = " + jon);
			System.out.println("id : " + this.rmServer_objId_Map.get(js));
			System.out.println("rmObj : " + this.objId_rmObject_Map.get(this.rmServer_objId_Map.get(js)));
			this.symbolicName_objId_Map.put(jon, this.rmServer_objId_Map.get(js));
			this.objId_rmObject_Map.put(this.rmServer_objId_Map.get(js), jo);
		}
		return;
  }
  
  /**
  * Get the reference of a JVN object managed by a given JVN server 
  * @param jon : the JVN object name
  * @param js : the remote reference of the JVNServer
  * @throws java.rmi.RemoteException,JvnException
  **/
  public JvnObject jvnLookupObject(String jon, JvnRemoteServer js)
  throws java.rmi.RemoteException,jvn.JvnException{
    // to be completed 
	if(this.symbolicName_objId_Map.containsKey(jon)){
		this.rmServer_objId_Map.put(js, this.symbolicName_objId_Map.get(jon));
		return this.objId_rmObject_Map.get(this.symbolicName_objId_Map.get(jon));
	}
	  
    return null;
  }
  
  /**
  * Get a Read lock on a JVN object managed by a given JVN server 
  * @param joi : the JVN object identification
  * @param js  : the remote reference of the server
  * @return the current JVN object state
  * @throws java.rmi.RemoteException, JvnException
  **/
   public Serializable jvnLockRead(int joi, JvnRemoteServer js)
   throws java.rmi.RemoteException, JvnException{
		// to be completed 
	   switch(this.objectsState){
		case R:
			this.rmServer_objId_Map.put(js, joi);
			return State.R;
		case W:
			if(this.rmServer_objId_Map.entrySet().size() > 1) throw new JvnException("[ServerStack & jvnLockRead()] State error in JvnCoordImpl");
			for(Object S_O : this.rmServer_objId_Map.entrySet()){
				Map.Entry<JvnRemoteServer , Integer> rmServer_objId = (Entry<JvnRemoteServer, Integer>) S_O;
				rmServer_objId.getKey().jvnInvalidateWriterForReader(rmServer_objId.getValue());
			}
			this.rmServer_objId_Map.put(js, joi);
			return State.R;
		default:
			throw new JvnException("[jvnLockRead()] State error in JvnCoordImpl");
		}
   }

  /**
  * Get a Write lock on a JVN object managed by a given JVN server 
  * @param joi : the JVN object identification
  * @param js  : the remote reference of the server
  * @return the current JVN object state
  * @throws java.rmi.RemoteException, JvnException
  **/
   public Serializable jvnLockWrite(int joi, JvnRemoteServer js)
   throws java.rmi.RemoteException, JvnException{
		// to be completed 
	   switch(this.objectsState){
		case R:
			for(Object S_O : this.rmServer_objId_Map.entrySet()){
				Map.Entry<JvnRemoteServer , Integer> rmServer_objId = (Entry<JvnRemoteServer, Integer>) S_O;
				rmServer_objId.getKey().jvnInvalidateReader(rmServer_objId.getValue());
			}
			this.rmServer_objId_Map = new HashMap<JvnRemoteServer , Integer>();
			this.rmServer_objId_Map.put(js, joi);
			return State.W;
		case W:
			if(this.rmServer_objId_Map.entrySet().size() > 1) throw new JvnException("[ServerStack & jvnLockWrite()] State error in JvnCoordImpl");
			for(Object S_O : this.rmServer_objId_Map.entrySet()){
				Map.Entry<JvnRemoteServer , Integer> rmServer_objId = (Entry<JvnRemoteServer, Integer>) S_O;
				rmServer_objId.getKey().jvnInvalidateWriter(rmServer_objId.getValue());
			}
			this.rmServer_objId_Map = new HashMap<JvnRemoteServer , Integer>();
			this.rmServer_objId_Map.put(js, joi);
			return State.W;
		default:
			throw new JvnException("[jvnLockRead()] State error in JvnCoordImpl");
		}
   }

	/**
	* A JVN server terminates
	* @param js  : the remote reference of the server
	* @throws java.rmi.RemoteException, JvnException
	**/
    public void jvnTerminate(JvnRemoteServer js)
	 throws java.rmi.RemoteException, JvnException {
		// to be completed 
    	for(Object S_O : this.rmServer_objId_Map.entrySet()){
			Map.Entry<JvnRemoteServer , Integer> rmServer_objId = (Entry<JvnRemoteServer, Integer>) S_O;
			if(rmServer_objId.getKey() == js)
				this.rmServer_objId_Map.remove(rmServer_objId.getKey());
		}
    }
}

 
