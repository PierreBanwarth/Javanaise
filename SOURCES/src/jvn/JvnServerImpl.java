/***
 * JAVANAISE Implementation
 * JvnServerImpl class
 * Contact: 
 *
 * Authors: 
 */

package jvn;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.util.*;






public class JvnServerImpl 	
              extends UnicastRemoteObject 
							implements JvnLocalServer, JvnRemoteServer{
	
  // A JVN server is managed as a singleton 
	private static JvnServerImpl js = null;
	
  // Location of rmiregistry
  private String hostName = "//localhost:5555/COORDINATOR/";
	
  // Location of The coordinator in the rmiregistry
  private JvnRemoteCoord coordinator;

  // Table containing <Id , JvnObject> couples
  private Hashtable<Integer, JvnObject> objectsTable;
  
  
  /**
  * Default constructor
  * @throws JvnException
  **/
	private JvnServerImpl() throws Exception {
		super();
		// to be completed 

		// Get the coordinator reference
		this.coordinator = (JvnRemoteCoord) Naming.lookup(hostName);
		
		// Create the objects Table
		this.objectsTable = new Hashtable<Integer, JvnObject>();
	}
	
  /**
    * Static method allowing an application to get a reference to 
    * a JVN server instance
    * @throws JvnException
    **/
	public static JvnServerImpl jvnGetServer() {
		if (js == null){
			try {
				js = new JvnServerImpl();
			} catch (Exception e) {
				return null;
			}
		}
		return js;
	}
	
	/**
	* The JVN service is not used anymore
	* @throws JvnException
	**/
	public  void jvnTerminate()
	throws jvn.JvnException {
		// to be completed 
		try {
		} catch (Exception e) {
			throw new JvnException("jvnTerminate: "+e);
		}
	} 
	
	/**
	* creation of a JVN object
	* @param o : the JVN object state
	* @throws JvnException
	**/
	public  JvnObject jvnCreateObject(Serializable o)
	throws jvn.JvnException { 
		// to be completed 
		int joi = 1;
		JvnObject jo = null;
		//System.out.println("createObject in JSI : "+ o + " :class: " + o.getClass());
		
		if(o.getClass().equals(irc.Sentence))
		
		try {
			// If we get an id, create a new jvnObject ( with write lock by default) AND
			// create a new entry in the lock and object cache
	
		} catch (Exception e) {
			throw new JvnException("jvnCreateObject: "+e);
		}
		
		return jo; 
	}
	
	/**
	*  Associate a symbolic name with a JVN object
	* @param jon : the JVN object name
	* @param jo : the JVN object 
	* @throws JvnException
	**/
	public  void jvnRegisterObject(String jon, JvnObject jo)
	throws jvn.JvnException {
		// to be completed 
		try {
			// create the shared object in the coordinator store
			this.coordinator.jvnRegisterObject(jon, jo, js);
		} catch (Exception e) {
			throw new JvnException("jvnRegisterObject: "+e);
		}
	}
	
	/**
	* Provide the reference of a JVN object beeing given its symbolic name
	* @param jon : the JVN object name
	* @return the JVN object 
	* @throws JvnException
	**/
	public  JvnObject jvnLookupObject(String jon)
	throws jvn.JvnException {
		// to be completed 
	}	
	
	/**
	* Get a Read lock on a JVN object 
	* @param joi : the JVN object identification
	* @return the current JVN object state
	* @throws  JvnException
	**/
   public Serializable jvnLockRead(int joi)
	 throws JvnException {
		// to be completed 
	   
	   try{
		   
		} catch (Exception e) {
			throw new JvnException("jvnLockRead in jvnServerImpl: "+e);
		}

	}	
	/**
	* Get a Write lock on a JVN object 
	* @param joi : the JVN object identification
	* @return the current JVN object state
	* @throws  JvnException
	**/
   public Serializable jvnLockWrite(int joi)
	 throws JvnException {
		// to be completed 
	   try{
	   

		} catch (Exception e) {
			throw new JvnException("jvnLockWrite in jvnServerImpl: "+e);
		}
	}	

	
  /**
	* Invalidate the Read lock of the JVN object identified by id 
	* called by the JvnCoord
	* @param joi : the JVN object id
	* @return void
	* @throws java.rmi.RemoteException,JvnException
	**/
  public void jvnInvalidateReader(int joi)
	throws java.rmi.RemoteException,jvn.JvnException {
		// to be completed 
	};
	    
	/**
	* Invalidate the Write lock of the JVN object identified by id 
	* @param joi : the JVN object id
	* @return the current JVN object state
	* @throws java.rmi.RemoteException,JvnException
	**/
  public Serializable jvnInvalidateWriter(int joi)
	throws java.rmi.RemoteException,jvn.JvnException { 
		// to be completed 
	};
	
	/**
	* Reduce the Write lock of the JVN object identified by id 
	* @param joi : the JVN object id
	* @return the current JVN object state
	* @throws java.rmi.RemoteException,JvnException
	**/
   public Serializable jvnInvalidateWriterForReader(int joi)
	 throws java.rmi.RemoteException,jvn.JvnException { 
		// to be completed 
	 };

}

 