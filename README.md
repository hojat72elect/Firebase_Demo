## Firebase Demo:

This project is my template for implementing as many Firebase services in a single app as I can.

-------------------------------------------

## Cloud Firestore:

<a href="https://firebase.google.com/docs/firestore">Cloud Firestore</a> is a NoSQL, cloud-hosted, flexible and scalable
database with offline support backed by Google. It's an easy solution for performing all the back-end part of your
business.

* NoSQL:<br/>
  In a NoSQL database, instead of managing data in tables with rows and columns (as in relational databases), it's
  stored in "documents" which contain a series of key-value pairs 👇👇<br/><br/>
  <img alt="document" src="DocumentationAssets/document.png"  width="40%" height="40%"/><br/><br/>
  The key-value pairs inside a document are referred to as "fields". Documents are basically some updated JSON files
  which support extra data types and also one single document can't be bigger than 1MB.<br/><br/>A document can store
  data types such as Strings, Integers, Booleans, Arrays, nested objects, raw binary values, coordinates, and some other
  complex data structures. <br/>
  In this context, nested objects are called <b>maps</b>; for example, in document above, "name" is a map with 2 parts
  for "first" and "last".<br/><br/>
  Documents are organized into "Collections" 👇👇<br/><br/>
  <img alt="collection" src="DocumentationAssets/collection.png"  width="30%" height="30%"/><br/><br/>
  Collections serve as folders for our documents. As you see above, each document within a collection has a unique ID;
  you can either choose ID of the documents yourself or let firestore automatically generate a random ID for them.

#### The root of a NoSQL database is always a collection, even if it only contains a single document.

The firestore database is schema-less (exactly on the opposite of XML based databases) which means we have the freedom
of putting any fields or data types into each separate document. We don't necessarily have to put the same fields into
documents of the same collection. For example 👇👇<br/><br/>
<img alt="users collection" src="DocumentationAssets/users_collection.png"  width="60%" height="60%"/><br/><br/>
In the collection of "users" we have above; we can later add more fields to some of those user documents without
breaking anything. In the collection of "users" we have above; we can later add more fields to some of those user
documents without facing errors.

#### Warning: For querying purposes, it's usually better to have the same fields over multiple documents.

<br/>
<img alt="warnings about collections in NoSQL database" src="DocumentationAssets/collection_donts.png"  width="60%" height="60%"/><br/><br/>
👆👆 Collections can't contain anything other than documents; no raw fields, nor other collections. A collection can only contain 1 or more documents.
<br/><br/>
<img alt="warnings about documents in NoSQL database" src="DocumentationAssets/document_donts.png"  width="40%" height="40%"/><br/><br/>
👆👆 A document can't contain other documents. However, a document can contain sub-collections 👇👇
<br/><br/>
<img alt="sub-collections of documents of a collection" src="DocumentationAssets/sub_collection.png"  width="40%" height="40%"/><br/><br/>
It's often the case to have your NoSQL database as a collection that contains documents that contain sub collection that contains documents and so on, so forth. For example 👇👇
<br/><br/>
<img alt="example database of a chatroom app" src="DocumentationAssets/chatroom_example.png"  width="60%" height="60%"/><br/><br/>
In the example picture above, we have the NoSQL database of a chat app that saves chatrooms in a collection called "rooms" where each room is represented by a document. And since firestore is optimized to host a large number of small documents (like tens of millions or even billions of small documents), we have stored each chat message in a separate document (all messages are in a collection named "messages").<br/><br/>
Collections and documents are created <b>implicitly</b>, we simply create a reference to them and set a value on that reference. If that document/collection doesn't exist, it'll be created; and if it exists, it'll be updated. Furthermore, when you delete all the documents within a collection, the collection itself will be deleted as well.

##### FireStore database also works offline.

Ofcourse you can't update anything in the cloud without internet connection but we have a copy of currently used
firestore database, cached on the device. Your app can query, listen, and make changes to this cached database. As soon
as user get backs online, this offline database will be synchronized with the cloud. For more info, have a
look <a href="https://firebase.google.com/docs/firestore/manage-data/enable-offline">here</a>.

-------------------------------------------

##### How to setup firestore in an Android project

For latest info about how to add firestore or other services of firebase to an Android project,
look <a href="https://firebase.google.com/docs/android/setup">here</a>.