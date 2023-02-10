
## Distributed Java Programming (G4INFPDJ)

---
### CM 3: Networking 
1. Networks
2. TCP/IP
3. TCP connections (Sockets)
4. UDP connections (DatagramSocket)
5. Multicast Connections (MulticastSocket)
6. RMI

Further reading, [Java Documentation](https://docs.oracle.com/javase/tutorial/networking/index.html)
---
### TCP/IP 4 layer model

- _Application_ : HTTP, ftp, telnet, etc.
- _Transport_ : TCP, UDP
- _Network_ : IP
- _Link_ : Device, driver, etc. 

__TCP__ :   Transmission Control Protocol

__UDP__ : User Datagram Protocol

---
### TCP
- _Connection_ based protocol
- Establish a __bidirectional__ communication
- Point-to-point channel for applications that require __reliable communications__
- Examples of applications: HTTP, FTP. 
- The ordering of the packages is guaranteed. 

---
### UDP
- It is __not__ connection based
- Think about sending a letter
- The order of delivery of the _datagrams_ is not guaranteed. 

---
### Ports
- Computers usually have a single _physical_ network connection.
- The computer is identified by its _IP address_
- IP address : identifier for each devise in a network using the  _Internet Protocol_ (IP). 
- IPv4 : 4 numbers of one byte each  (32 bits) `216.239.38.120`
- However, there are many applications using the same server. 
- _Ports_ ( 0 to 65,535) allow to know the intended application for a package received.
-  __Well-known ports__ (0 - 1023): e.g., http (80), ssh(21), etc. 

---
### Sockets
- TCP: connection based protocol
- The server uses a _port_ to register a service
- The client uses a __socket__ to establish a communication. 
- The connection is bidirectional (the client and the server can read and write on the socket). 
- The socket is the _end-point_ of the two-way communication. 

---
### Client and Server
- The server has a socket that is __bound__ to a specific port number.
- The client sends a _connection request_, binding a _local port number_ used to establish the connection. 
- If the server _accepts_ the connection, the server creates a __new socket__ to communicate with the client
- The server may continue _listening and waiting_ for other connections. 
- The created socket and the socket in the client can be used for bidirectional communication. 
---
### Client and Server

> An endpoint is a combination of an IP address and a port number. Every TCP
> connection can be uniquely identified by its two endpoints.

---
### Example 1: Connection
1. The server uses the class `ServerSocket`
2. It `accept`s new connections (and waits).
3. The connection is established
4. Both programs terminate.

> We shall use `127.0.0.1` as network interface (the _loopback_ interface). 

---
### Example 1: Connection
#### The server (V1)
```java
	ServerSocket server;
	try {
		//  Opening the port 12345
		server = new ServerSocket(12345);

		System.out.println("Waiting for connections");
		// Accept blocks until a new connection arrives
		Socket client = server.accept();
		// Print some information from the client
		System.out.println("Client [connected]: " + client.getInetAddress().getHostAddress());

		// Ending the service
		server.close();
	} catch (IOException E) {
		E.printStackTrace();
	}
```
---
### Example 1: Connection
#### The client (V1)
```java
try {
	// Establishing a connection with the server
	Socket s_client = new Socket("127.0.0.1", 12345);
	System.out.println("Connection [OK]");
	// Closing the socket
	s_client.close();
```
---
### Example 2: Sending data

- The socket can be used for both, read and write information.
- A protocol needs to be defined:
  * What kind of data will be sent?
  * Who starts?
  * What is the order of the messages?

---
### Example 2: Sending data
This is a very simple protocol:

1. A connection is established
2. The client sends a message
3. The server prints the message and ends. 
---
### Example 2: Sending data
#### Server (v2)
```java
server = new ServerSocket(12345);
System.out.println("Waiting for connections...");
Socket client = server.accept();
System.out.println("Connection  [OK] ");

// Create an input stream from the input stream of the socket
DataInputStream input_client = new DataInputStream(client.getInputStream());
System.out.println("Message: " + input_client.readUTF());
```

---
### Example 2: Sending data
#### Client (v2)
```java
Socket s_client = new Socket("127.0.0.1", 12345);
System.out.println("Connection [OK]");

// Creating an outputstream to send data
DataOutputStream saida = new DataOutputStream(s_client.getOutputStream());
// Send a message 
saida.writeUTF("Hello World... now on a socket ;-)");
System.out.println("Message sent!");
```
---
### Example 2: Sending data
#### A client in Python
```python
HOST = '127.0.0.1'  
PORT = 12345

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
    s.sendall(b'A message from Python!\n')
```

However:
```
Connection  [OK] 
java.io.EOFException ...
    at java.io.DataInputStream.readFully(DataInputStream.java:197)
    at java.io.DataInputStream.readUTF(DataInputStream.java:609)
```
---
### Example 2: Sending data
#### A client in Python

We need to use a more "low level" class in Java to
receive strings: 
```java
BufferedReader input_client = new BufferedReader(new InputStreamReader(client.getInputStream()));
String txt = input_client.readLine();
System.out.println("Message: " + txt);
```

Now it works!
```
Connection  [OK] 
Message: A message from Python!
```
---
### Example 3: A little protocol
1. The client sends the name of a person
2. The client sends the birth date 
3. The server creates the person in the database
4. The server sends to the client the ID of the person
5. The protocol ends. 

> Additionally, we consider that  the server may receive _several requests_

---
### Example 3: A little protocol
How can we send a "Date"?
- Interface `Serializable`
- _Serialization_: representing an object as a sequence of bytes
- _Deserialization_: from a sequence of bytes to objects
- `ObjectOutputStream` and `ObjectInputStream`: high level classes for reading/writing (serialized) objects. 

>Note that the class `Date` implements `Serializable`

---
### Example 3: A little protocol
- The class `Person` is as expected.
- It uses the class `UUID` (that implements `Serializable`) to produce unique identifiers. 

```java
private UUID id;

public Person(String name, Date date){
	this.name = name;
	this.date = date;
	this.id = UUID.randomUUID();
}
```

---
### Example 3: A little protocol
The protocol is implemented in the class `ServerTask`

```java
class ServerTask{
 private Socket client;
 public ServerTask(Socket client){
     this.client = client;
 }

 public Person execute(){
  try{
      ObjectInputStream input_client = new ObjectInputStream(client.getInputStream());
      ObjectOutputStream output_client = new ObjectOutputStream(client.getOutputStream());
      // 1. Receive the name
      String name = input_client.readUTF();
      // 2. Receive a date
      Date date = (Date) input_client.readObject();
      // 3. Create the person
      Person P = new Person(name, date);
      // 4. Send to the client the unique identifier
      output_client.writeObject(P.getId());
      this.client.close();
      return P;
  }
```
---
### Example 3: A little protocol
- The server is an infinite loop waiting for connections:
- It stores a collection of people

```java
static List< Person > database = new ArrayList<>();

public static void main(String arg[]) {
	ServerSocket server;
	try {
		server = new ServerSocket(12345);
		System.out.println("Waiting for connections...");
		while(true){
			Socket client = server.accept();
			System.out.println("Connection  [OK] ");
			ServerTask T = new ServerTask(client);
			Person P = T.execute();
			Server.database.add(P);
			System.out.println(Server.database);
		}
```
---
### Example 3: A little protocol
The client must follow the protocol
```java
String name = arg[0];
SimpleDateFormat DF = new SimpleDateFormat("yyyy/mm/dd");
Date date = DF.parse(arg[1]);
Socket s_client = new Socket("127.0.0.1", 12345);
System.out.println("Connection [OK]");
// Creating an outputstream to send data
ObjectOutputStream output = new ObjectOutputStream(s_client.getOutputStream());
ObjectInputStream input = new ObjectInputStream(s_client.getInputStream());
// Send a message 
output.writeUTF(name);
output.writeObject(date);
// Receiving the ID
UUID id = (UUID) input.readObject();
System.out.println("ID obtained: " + id );
```
---
### Example 4: Concurrent Server
#### Server 
- Now the server may process several request concurrently
- The management of threads is delegated to  `ExecutorService` 

#### Client
- __For illustration__, the client sends several request to the server
- The class `CompletionService` is used to _synchronize all the requests_. 
---
### Example 4: Concurrent Server
#### ServerTask
```java
class ServerTask implements Runnable{
    private Socket client;
    public ServerTask(Socket client){
        this.client = client;
    }

    public void run(){
        try{
            System.out.println("[START] new client.");
            ObjectInputStream input_client = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream output_client = new ObjectOutputStream(client.getOutputStream());
            // 1. Receive the name
            String name = input_client.readUTF();
			...
            Person P = new Person(name, date);
            // Simulating a "heavy" task
            Thread.sleep(2000);
            // 4. Send to the client the unique identifier
            output_client.writeObject(P.getId());
            this.client.close();
            Server.database.add(P);
        }
```
---
### Example 4: Concurrent Server
#### Server
```java
public Server() throws IOException{
	this.server = new ServerSocket(Server.PORT);
	this.executor = Executors.newFixedThreadPool(Server.NUMPROCS);
}

public void start(){
	try {
		System.out.println("Waiting for connections...");
		while(true){
			this.executor.execute(new ServerTask(server.accept()));
			System.out.println("Connection  [OK] ");
		}

	} catch (IOException E) {
		E.printStackTrace();
	}
}

```
---
### Example 4: Concurrent Server
#### Client
> The client uses threads in this case only for illustration purposes.

```java
ExecutorService executor = Executors.newFixedThreadPool(10);
CompletionService< UUID > completionService = 
	new ExecutorCompletionService<>(executor);

// Creating N random people
for(int i = 1; i <= NPEOPLE; i++) {
	completionService.submit(new Callable< UUID >() {
		public UUID call() {
			try{
				String name = "Person-" + (R.nextInt()%1000);
				// Up to approx 20 years ago
				long L1 = Math.abs(R.nextLong()) % (20*365*24*3600*1000L);
				Date D = new Date(System.currentTimeMillis() - L1);
				UUID id = Client.createPerson(name, D);
				return id;
			}
			catch(Exception E){ }
			return null;
		}
	}
	);
}
// Receiving and printing the IDs created
for(int i = 1; i <= NPEOPLE; i++) {
	Future< UUID > result = completionService.take(); 
	System.out.println(result.get());
}

```
---
### Example 4: Concurrent Server
_Synchronization_:
1. The client sends several requests
2. The instance of `CompletionService` submits new tasks
3. The _consumer_ can take completed tasks (__Future__)
4. This class manages the  internal completion queue.

> _Asynchronous tasks_: The order in which the IDs arrive cannot be known/controlled. 

---
### Output and Input Streams
- Note the use of `DataOutputStream` and
  `ObjectOutputStream`
- Those are convenient subclasses of `OutputStream` to
  write data types.
- There is no need to deal with end of strings and
  formatting. 
- For dealing with only text processing,
  `BufferedOutpoutStream` is a good choice. 
- Similarly for the `InputStream` hierarchy. 

---
### Datagrams

- The arrival and order of arrival are not guaranteed. 
- _UDP_: Protocol for sending packages (__datagrams__)
- Useful when there is no need of a dedicated _point-to-point_ channel

---
### Datagrams

#### Server
- A `DatagramSocket` is opened on a port
- It waits for a package/request to arrive (`receive`)
- The address and port used for the client are obtained (attached to the request). 
- A `DatagramPacket` is prepared and sent to the client

---
### Datagrams
#### Server
```java
public Server() throws IOException{
	socket = new DatagramSocket(Server.PORT);
}

public void run(){
	while(true){
		try{
			// receive request
			byte [] buffer = new byte[256];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			
			// Sending a message
			InetAddress address = packet.getAddress(); // Address of the incoming packet
			int port = packet.getPort(); // port of the incoming packet

			System.out.println("[CLIENT]: " + address + " : " + port);

			byte [] output = new Date().toString().getBytes();
			packet = new DatagramPacket(output, output.length, address, port);
			socket.send(packet);
```
---
### Datagrams
#### Client
- It creates a `DatagramSocket` _without port_
- An available port is chosen. 
- It prepares a datagram (the __request__)
- The datagram is sent to the server
- It waits for the answer (in the form of a datagram)

---
### Datagrams
#### Client
```java
DatagramSocket socket = new DatagramSocket();

// Preparing the request to the server
byte[] buffer = new byte[256];
InetAddress address = InetAddress.getByName("127.0.0.1");
DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 12345);
socket.send(packet);

// Receiving the answer from the server
packet = new DatagramPacket(buffer, buffer.length);
socket.receive(packet);

String answer = new String(packet.getData(), 0, packet.getLength());
System.out.println(answer);
```
---
### Broadcasting
- The server sends messages to a group of clients.
- The clients _join_ the group
- A _multicast_ address must be used `224.0.0.0`
---
### Broadcasting
#### Server

1. It opens any port to send the messages
2. It send messages to a particular port (that the _clients knows_)

```java
while(true){
 try{
	// Sending a message
	InetAddress address = InetAddress.getByName("224.0.0.0"); 

	byte [] output = new Date().toString().getBytes();
	DatagramPacket packet = new DatagramPacket(output, output.length, address, Server.PORT);
	socket.send(packet);
	Thread.sleep(R.nextInt(4000));
```

---
### Broadcasting
#### Client
- Opens a `MulticastSocket`
- Join the group

```java
MulticastSocket socket = new MulticastSocket(12345);
// Joining the group 
socket.joinGroup(InetAddress.getByName("224.0.0.0"));

while(true){
	byte[] buffer = new byte[256];
	DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
	socket.receive(packet);
	String answer = new String(packet.getData(), 0, packet.getLength());
	System.out.println(answer);
}
```
---
### RMI
- _Remote Method Invocation_
- The object oriented version of RPC (__Remote Procedure Call__)
- Objects are registered on a _name service_
- The client obtains a _remote reference_ to the object
- Calls to methods of the (_remote_) object look  __local__!

---
### RMI
The RMI protocols builds on top of:
- Java Object _Serialization_: marshaling  and returning data
- The HTTP protocol: to _POST_ remote method invocations. 
---
### RMI
First an _interface_ must be defined: 

```java
public interface PersonService extends Remote {
        // Adding a new person to the database
        void addPerson(String name, Date bday) throws RemoteException;
        // List the people whose name is name
        List< Person > search(String name) throws RemoteException;
}

```

---
### RMI
Since one of the methods require sending objects of type `Person`, such
class must implement the interface `Serializable`

```java
public class Person implements Serializable{
    private String name;
    private Date bday;
```
---
### RMI
The server implements the methods of the interface `PersonService`

```java
public class Server implements PersonService {
	private List< Person > database = new ArrayList<>();
    public Server() {}

	@Override
    public synchronized void addPerson(String name, Date bday){
		this.database.add(new Person(name, bday));
	}

    @Override
    public synchronized List< Person > search(String name){
        return this.database.stream()
                .filter( p -> p.getName().contains(name))
                .collect(Collectors.toList())
                ;
    }
```

---
### RMI
The server must also:
1. _Export_ the object, thus allowing it to receive incoming __remote__ invocations
2. Behind the scenes, this means opening a TCP port to handle connections, sending data, etc. 
3. _Register_ the object on a _name service_. Hence, the client can easily find it by using a name!

---
### RMI
```java
Server server = new Server();
// Making the object available for remote invocations
PersonService stub = (PersonService) UnicastRemoteObject.exportObject(server, 12345);

// Bind the remote object's stub in the registry
Registry registry = LocateRegistry.getRegistry();
registry.bind("Server", stub);
```
---
### RMI
The client makes calls to methods as if the object were local:
```java
// Service of names
Registry registry = LocateRegistry.getRegistry("127.0.0.1");
// Finding the remote object
PersonService stub = (PersonService) registry.lookup("Server");
// Creating (by invoking a remote method) 1000 people
for(int i=1;i<=1000;i++){
	stub.addPerson("Person-" + R.nextInt(2000),new Date());
```

The representation of the remote object is usally called _proxy_ or _stub_.

---
### RMI
For executing the example:
1. Run `rmiregistry` (register service, running on port 1099)
2. Run the server
3. Run Client1  (adding new people to the database)
4. Run Client2 (querying the database)
---
### RMI
- Remote calls from different clients might be executed concurrently. 
- Better to avoid _race conditions_!
- Interoperability with other languages is possible (CORBA / IIOP)
---
### RMI
- An access to a  _remote object_, implementing `Remote`,  is by reference : Clients may change the state
of the server by calling its methods. 
- Objects as parameters and returned, using `Serializable`, are "copies" (by value). 
---
### RMI Callbacks

Suppose that we need an object to be notified when a new person is added:

- We may send requests to the server each T seconds
- But this is clearly not a good design
- What about callbacks and notifications?

---
### RMI Callbacks
- A _listener_ (client) will register in the server
- The server __notifies__ all the registered clients
- The client needs to execute a method from the server (`add`)
- The server needs to execute a method from the client (`notify`)

---
### RMI Callbacks
Two interfaces : one implemented by the client and one  implemented by the server. 

_Server side_
```java
public interface PersonService extends Remote {

        // Adding a new person to the database
        void addPerson(String name, Date bday) throws RemoteException;

        // Adding a new client willing to be notified when new people is added
        void addListener(PersonMonitor PM) throws RemoteException;
}

```

---
### RMI Callbacks
Two interfaces : one implemented by the client and one  implemented by the server. 

_Client side_
```java
public interface PersonMonitor extends Remote {
        // Notifying when a new person is added
        void notifyNewPerson(Person P) throws RemoteException;
}
```

---
### RMI Callbacks

The server implements `PersonService`:
```java
public class Server implements PersonService {
    private List< Person > database = new ArrayList<>();
    private List< PersonMonitor > listeners = new ArrayList<>();

    @Override
    public synchronized void addPerson(String name, Date bday){
        System.out.println("["+name+"] added");
        Person P = new Person(name,bday);
        this.database.add(P);
        this.notifyAll(P);
    }

    private void notifyAll(Person P){
        for(PersonMonitor listener : listeners){
            try{
                listener.notifyNewPerson(P);
            }
            catch(RemoteException E){
                System.out.println(E);
            }
        }
    }
```
---
### RMI Callbacks

The server implements `PersonService`:
- Note that, after adding a person, all the listeners are notified
- The server is calling a  method from a remote object 

---
### RMI Callbacks

For the client, we need to specify that it is a remote object:
- Either we can register it in the name service
- Or we make it a subclass of UnicastRemoteObject
- Otherwise, it will be simply "copied" (after serialized). 

```java
public class Monitor extends UnicastRemoteObject 
                     implements PersonMonitor {

    public Monitor() throws RemoteException{
    }

    @Override
    public  void notifyNewPerson(Person P){
        System.out.println("["+P.getName()+"] was added");
    }

}
```
---
### RMI Callbacks
For executing the example:
1. Run `rmiregistry` (register service, running on port 1099)
2. Run the server
3. Run ClientAdd  (adding new people to the database)
4. Run ClientMonitor (to be notified)
