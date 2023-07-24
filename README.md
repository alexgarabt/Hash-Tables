# Hash-Tables
### What are the hash tables??
> Also known as hash map, is a _"complex" data structure_ that implements an associative array or dictionary. It is an abstract data type that maps keys to values, MAP<Key,Value>. [Wikipedia][wikipedia hash table].


* Hash tables are really useful in terms of efficiency ***(time complexity)*** for ADT like set, map and dictionary.
*  It reduces many operations to O(1). More detailed in the [Complexity.pdf][path complexity file] file.
--- 
Using **arrays** and **linked lists** as primarly data structure and some ideas, more explained in the code of each one. We have to types of hash tables:

>**Chaining** and **Open** addressing.
* The **Chaining** strategy solves the problem of collisions allowing more than one element to be stored in each cell in the table *(array)*, using *linked lists*.
* The **Open** strategy solves the collision problem by establishing a sequence of positions (scan path) in which you can find an element in the table.
  * **Multiple cells available for each item**
  * Each table cell can be in 3 different **states**:
    * **Full**: Contains an element, cannot be inserted into it.
    * **Empty**: Can be inserted into it, stops scanning.
    * **Deleted**: It can be inserted into it, it does not stop the scan.
---
[wikipedia hash table]: https://en.wikipedia.org/wiki/Hash_table
[path complexity file]: Complexity.pdf
