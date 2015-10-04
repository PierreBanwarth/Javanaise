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
	return joi;
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
	return joi;
		// to be completed 
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

 
