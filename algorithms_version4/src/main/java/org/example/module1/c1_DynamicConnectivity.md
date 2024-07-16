# c1_DynamicConnectivity.txt

Welcome back to algorithms.

Today, we're going to talk about the union find problem.

A set of algorithms for solving the so-called dynamic connectivity problem.

We'll look at two classic algorithms.

Quick Find and Quick Union, and some applications and improvements of those algorithms.

The subtext of today's lecture really is to go through the steps that we'll follow over and over again to develop a useful algorithm.

The first step is to model the problem.

Try to understand, basically, what are the main elements of the problem that need to be solved.

Then we'll find some algorithm to solve the problem.

In many cases, the first algorithm we come up with would be fast enough and maybe it fits in memory and, we'll go ahead and use it, and be off and running.

But in many other cases maybe it's not fast enough, or there's not enough memory.

So, what we do is try to figure out why, find a way to address whatever's causing that problem, find a new algorithm and iterate until we're satisfied.

This is the scientific approach to designing and analyzing algorithms, where we build mathematical models to try and understand what's going on, and then we do experiments to validate those models and help us improve things.

So, first we'll talk about the dynamic connectivity problem, the model of the problem for union find.

So, here's the idea.

They're going to have a set of N objects.

Doesn't really matter what they are.

We're going to use the numbers, zero through N to model our objects.

And then, we have the idea of a connection between two objects.

And, we'll, postulate that there's going to be a command that says, connect two objects.

Given two objects, provide a connection between them.

And then key part of the problem is find query or the connected query, which just asks, is there a path connecting the two objects.

So for example, in this set of ten objects, we performed already, a bunch of union commands, connecting four and three, three and eight, six and five, nine and four, two and one.

And now we might have a connected query that says, is zero connect ed to seven? Well, in this case, there is no connection, so we say no.

But if we ask is eight connected to nine? We are going to say yes, even no we don't have a direct connection between eight and nine.

There is a path from eight to three to four to nine.

So, that's our problem, to be able to officially support these two commands for given set of objects.

Now, let's say we add a union five, zero.

So, that creates a connection between five and zero.

Seven and two creates a connection between seven and two.

And six and one, between six and one.

So, now if we ask our zero connected to seven, well one and zero we can do that too.

And that's a redundant connection.

And now, if we ask is zero connected to seven we're going to answer yes.

So that's our problem, intermix union, commands and connected queries and we need to be able to officially support those commands for a large number of objects.

So, here's a much bigger example.

And you can see that we're going to need efficient algorithms for this.

First of all, you can see we're going to need a computer for this.

It would take quite, quite some time for a human to figure out whether there's a connection.

In this case there is a connection.

Now, the algorithms that we're looking at today are not going to actually give the path connecting the two objects.

It's just going to be able to answer the question, is there a path? In part two of the course, we'll consider algorithms that explicitly find paths.

They're not as efficient as union find because they have more work to do.

Now, applications of these, these algorithms involve objects of all types.

These are used for digital photos, where the objects are pixels they're used for networks, where the objects are computers, social networks, where it's people, or computer chips, where it's circuit elements or abstract things like variable names in a program, or elements in a mathematical set, or physical things like metallic sites in a composite system.

So, all different types of objects for, but for programming we're going to associate each object with a name and we'll just name the objects with a number, integers from zero to N-1.

That's a very convenient initial starting point for our programs because we can use integers as an index into an array then, and then quickly access information relevant to each object.

And it also just supresses a lot of details that are not relevant to union find.

In fact, to make this mapping from an object name to the integer zero through N - one is to find application of a symbol table or a searching algorithm, which is one of the things that we'll be studying later in this course algorithms and data structures for solving that problem.

Now, the connections, well, we need, a few abstract properties that these connections have to satisfy.

And they're all quite natural and intuitive.

So we assume that is connected to is an equivalence relation.

That is, every object's connected to itself, it's symmetric.

If P's connected to Q, then Q's connected to P, and it's transitive.

If P's connected to Q, and Q's connected to R, then P's connected to R.

Now these properties are very intuitive.

But it's worthwhile to state them explicitly and make sure that our algorithms maintain them.

When we have an equivalence relation a set of objects and connections divide into subsets called connected components.

A connected component is a maximal set of objects that's mutually connected.

For example in this small example here, there's three connected components.

One consisting of just object zero, second one objects one, four and five.

And third one the other four objects.

And these components have the property that if any two objects in them are connected and there is no object outside that is connected to those objects, that's connected components.

Our algorithms will gain efficiency by maintaining connected components and using that knowledge to efficiently answer the query that's, that they're presented with.

Okay, so to implement the operations, we have to find query and the union command.

And so we're going to mai ntain the connected components.

The find is going to have to check if two objects are in the same component and the union command is going to have to replace components containing two objects with their union.

So, for example, if we have these components, and we get the command to union connect, two and five.

Essentially, we need to merge the connected components containing the one containing two or the one containing five to get a big connected components and now we have only two connected components.

All of that leads up to, in a programming world to specifying, a data type which is simply specification of the methods that we are want to going to implement in order to solve this problem.

So you know, typical Java model, what we will do is create a class called UF that contains two methods, one to implement union, the other one to implement connected, which returns a boolean.

The constructor, takes SR unit, the number of objects, so that it can build data structure based on the number of objects.

So, and we have to, bear in mind, as we're building our logarithms, that both the number of objects can be huge, but also, the number of operations.

We can have a, a very large number, of union and connected, operations and our algorithms are going to have to be efficient, under those conditions.

One of the practices that will follow often in this course is to check our API design before getting too far into dealing with the problem, by building a client that is going to use the data type that we develop.

So, for this example, we've got a client that, Will read information from standard input.

First, an integer which is the number of objects that are going to be processed.

And then a series of pairs of object names.

And what the client does is it, it'll, first it'll read the integer from standard input, and create a, a UF object.

And then as long as standard input is not empty, it's going to read two integers from the input.

And if they're not connected, then it'll connect them and print them out.

If they are connected it'll ignore.

So, that's our test client and that's a fine test client to make sure that any implementation does what we expect that it will.

So, that's the setup.

We've described the operations we want to implement all the way down to code and we have client code that we're going to have to be able to service with our


# C2_QuickFind.txt


Now we'll look at our first implementation of an algorithm for solving the dynamic connectivity problem, called Quick-find.

This is a so called eager algorithm, for solving kind activity problem.

The data structure that we're going to use to support the algorithm is simply an integer array indexed by object.

The interpretation is the two objects, P and Q are connected if and only if, their entries in the array are the same.

So for example in this example with our ten objects the idea array that describes the situation after seven connections is illustrated in the middle of the slide.

So that, after the, at this point zero, five, and six are all in the same connected component, because they have the same array entry, zero.

One, two, and seven all have entry one.

And three, four, eight, and nine all have entry eight.

So that representation is, shows that they're connected.

And clearly, that's going to support a quick implementation of the find operation.

We just check the array entries to see if they're equal.

Check if P and Q have the same ID.

So, six and one have different IDs.

One has ID one, six has ID zero.

They're not in the same connected component.

Union is more difficult in order to merge the components, containing two given objects.

We have to change all the entries, whose ID is equal to one of them to the other one.

And arbitrarily we choose to change the ones that are the same as P to the ones that are same as Q.

So if we're going to union six and one, then we have to change entries zero, five, and six.

Everybody in the same connected component as six.

From zero to one.

And this is, as we'll see, this is a bit of a problem when we have a huge number of objects, because there's a lot of values that can change.

But still, it's easy to implement, so that'll be our starting point.

So we'll start with a, a demo of how this works.

So, initially, we set up the ID array, with each entry, equal to its index.

And so all that says is that all the objects are independent.

They're in their own connected component.

Now, when we get a union operation.

So, say, four is supposed to be unio n with three.

Then we're going to change, all entries, whose ID is equal to the first ID to the second one.

So in this case, we'll change the, connect three and four means that we need to change the four to a three.

And we'll continue to do a few more so you'll get an idea of how it works.

So three and eight now so to connect three and eight now three and four have to be connected to eight.

So both of those entries have to change to eight.

Okay? So now, what about six and five? So again, we change the first one to match the second one.

So to connect six and five, we change the six to a five.

What about nine and four? So, now we have to change the, to connect nine and four, we have to change, 9's entry to be the same as 4's.

So now we have three, four, eight, and nine.

All have entries eight.

They're all on the same connected component.

Two and one means that we connect two and one by changing the 2201.

Eight and nine are already connected.

They have the same, entries in the idea array.

So, that connected query, that find says, true, they're already connected.

And five and zero have different entries.

They're not connected, so we'd return false, in that case, not connected.

And then, if we want to connect five and zero.

Then, as usual we'll connect, the entry corresponding to both five and six to zero.

Seven and two, union seven and two.

That's an easy one.

And union, six and one so there is three entries that have to get changed.

All those zeros have to get changed to ones.

So, that's a quick demo of Quick-find.

Now next we'll look at the code for implementating that.

Okay, with this concrete demo in mind then moving to coding up this algorithim is pretty straight forward.

Although it's an interesting programming exercise that a lot of us would get wrong the first time.

So let's start with the constructor, well we have a, a private integer array.

That's our ID array.

That's the data structure that's going to support this implementation.

The constructor has to create the array and then go through and set the value corresponding to each index I to I.

That's straight forward.

The find operation, or connected operation.

That's the easy one .

This is the Quick-find algorithm.

So it simply takes its two arguments, P and Q, and checks whether their ID entries are equal, and returns that value.

If they're equal, it returns true.

If they're not equal, it returns false.

The more complicated operation implement is a union.

And there, we find first the ID corresponding with the first argument, and then the ID corresponding to the second argument.

And then we go through the whole array, and looking for the entries whose IDs are equal to the ID of the first argument, and set those to the ID of the second argument.

That's a pretty straightforward implementation.

And I mentioned that a lot of us would get us wrong.

The mistake we might make is to put ID of P here rather than first picking out, that value.

And you can think about the implications of that.

That's an insidious bug.

So, that's a fine implementation of QuickFind so the next thing to decide is how effective or efficient that algorithm is going to be and we'll talk in some detail about how to do that but for this it's sufficient to just think about the number of times the code has to access the array.

As we saw when doing the implementation, both the initialized and union operations involved the for-loop that go through the entire array.

So they have to touch in a constant proportional to n times after touching array entry.

Find Operation is quick, it's just to a constant number of times check array entries.

And this is problematic because the union operation is too expensive.

In particular if you just have N union commands on N objects which is not unreasonable.

They're either connected or not then that will take quadratic time in squared time.

And one of the themes that we'll go through over and over in this course is that quadratic time is much to slow.

And we can't accept quadratic time algorithms for large problems.

The reason is they don't scale.

As computers get faster and bigger, quadratic algorithms actually get slower.

Now, let's just talk roughly about what I mean by that.

A very rough standard, say for now, is that people have computers that can run billions of operations per second, and they have billions of entries in main memory.

So, that means that you could touch everything in the main memory in about a second.

That's kind of an amazing fact that this rough standard is really held for 50 or 60 years.

The computers get bigger but they get faster so to touch everything in the memory is going to take a few seconds.

Now it's true when computers only have a few thousand words of memory and it's true now that they have billions or more.

So let's accept that as what computers are like.

Now, that means is that, with that huge memory, we can address huge problems.

So we could have, billions of objects, and hope to do billions of union commands on them.

And, but the problem with that quick find algorithm is that, that would take ten^18th operations, or, say array axises or touching memory.

And if you do the math, that works out to 30 some years of computer time.

Obviously, not practical to address such a problem on today's computer.

And, and the reason is, and the problem is that quadratic algorithms don't scale with technology.

You might have a new computer that's ten times as fast but you could address a problem that's ten times as big.

And with a quadratic algorithm when you do that.

It's going to be ten times as slow.

That's the kind of situation we're going to try to avoid by developing more efficient algorithms for solving problems like this.

# C3_QuickUnion


All right so QuickFind is too slow for huge problems.

So, how are we going to do better? Our first attempt is an alternative called, Quick-union.

This is so called lazy approach to algorithm design where we try to avoid doing work until we have to.

It uses the same data structure or array ID with size M but now it has a different interpretation.

We are going to think of that array as representing a set of trees that's called a forest as depicted at right.

So, each entry in the array is going to contain a reference to its parent in the tree.

So, for example, 3's parent is four, 4's parent is nine.

So 3's entry is four and 4's entry is nine in the array.

Now each entry in the array has associated with it a root.

That's the root of its tree.

Elements that are all by themselves in just, in their own connected component, point to themselves, so one points to itself but also nine points to itself.

It's the root of the tree, containing two, four and three.

So, from this data structure we can associate with each item a root, which is representative, say, of it's connected component.

So that's the root of three is nine, going up that root.

Now, once we can calculate these roots, then we can implement the find operation just by checking whether the two items that we're supposed to check with are connective where they have the same root.

That's equivalent to saying, are they in the same connective component? So that's some work, going to find the roots of each item but the union operation is very easy.

To merge components containing two different items.

Two items that are in different components.

All we do is set the ID of P's route to the ID of Q's route.

Let's make P's tree point to Q.

So in this case, we would change the entry of nine to be six to merge three and five.

The components containing three and five.

And with just changing one value in the array we get the two large components emerged together.

That's the Quick-union algorithm.

Because a union operation only involves changing one entry in the array.

Find operation requires a little more work.

So let's look at the Implementation, a demo of that one in operation first.

So again we, we start out the same way but now the idea array entry really means that every one of these things is a little tree where the one node each everyone pointing to itself.

It's the root of it's own tree so now if we have to put four and three in the same component, then all we do is we take the root, of the component containing the first item and make that a child of the root of the component, component containing the second item.

In this case we just make four as parent three.

So now three and eight.

So again, we take the first item and make it a child of the root of the tree containing the second item.

So now three, four, and eight are in the same component.

Six and five six goes below five.

Nine and four, So now four is the root of the tree containing four is eight.

And the root of tree containing nine is nine.

And so we make nine a child of eight.

Two and one, that's an easy one.

Now if we get our, our eight and nine connected, we just checked that they have the same root and they both have the same root eight and so they're connected.

Five and four 4's root is eight.

5's root is five.

They're different.

They're not connected.

Five and zero.

Five goes to be a child of zero.

Seven and two seven goes to be a child of 2's root which is one.

Six and one.

6's route is zero 1's its own route, so zero becomes a child of one.

Each one of these union operations just involves changing one entry in the array.

And finally, seven and three.

So seven's root is one, three's root is eight, one becomes a child of eight.

Okay and now we have one connected component with all the items together.

Alright, so now let's look at the code for implementing Quick-union.

The constructor is the same as the other one.

We create the array and then set each element to be it's own root.

Now we have a private method that implements this process of finding the root by chasing parent pointers until we get to the point where I is equal to ID of I, and if it's not equal, we just move I up one level in the tree, set I equals ID of I and return it.

So starting at any node, you just follow ID equals ID of I until they're equal and then you're at a root and that's a private method that we can use to implement the find operation or the connected operation.

You just find the root of P and the root of Q and if you check if they're equal.

And then the union operation is simply find the two roots I and then set the idea the first one could be the second one.

Actually less code than for Quick Find, no fore loops.

There's this one wild loop that we have to worry about a little bit.

But that's a quick and elegant implementation of code to solve the dynamic connectivity problem called Quick-union.

So now we're going to have to look at can this code be effective for large problems? Well unfortunately Quick-union is faster but it's also too slow.

And it's a little different kind of too slow then for Quick Find, there's times when it could be fast, but there's also times when it could be too slow.

And the defect for Quick-union is that the trees can get too tall.

Which would mean that the find operation would be too expensive.

You could wind up with a long skinny tree.

Of each object just pointing to next and then to do a find operation for object at the bottom would involve going all the way through the tree.

Costing involving in the ray axises just to do the find operation and that's going to be too slow if you have a lot of operations.


# C4_QuickUnionImpovements


Okay.

So, we've looked at the quick union and quick find algorithms.

Both of which are easy to implement.

But simply can't support a huge dynamic connectivity problems.

So, how are we going to do better? That's what we'll look at next.

A very effective improvement, it's called weighting.

And it might have occurred to you while we are looking at these algorithms.

The idea is to when implementing the quick union algorithm take steps to avoid having tall trees.

If you've got a large tree and a small tree to combine together what you want to try to do is avoid putting the large tree lower, that's going to lead to long tall trees.

And there's a relatively easy way to do that.

What we'll do is we'll keep track of the number of objects in each tree and then, we'll maintain balance by always making sure that we link the root of the smaller tree to the root of the larger tree.

So, we, we avoid this first situation here where we put the larger tree lower.

In the weighted algorithm, we always put the smaller tree lower.

How we, let's see how we implement that.

Let's see a demo first.

Okay, so again start out in our normal starting position, where everybody's in their own tree.

And for when there's only two items to link it, it works, works the same way as before.

But now, when we have eight to merge with four and three, we put the eight as the child, no matter which order their arguments came, because it's the smaller tree.

So, six and five doesn't matter, whichever one goes down doesn't matter.

Nine and four, so now, nine is the small one four is the big one.

So, nine is going to be the one that goes down below.

Two and one, five and zero.

So now, five and zero five is in the bigger tree so zero goes below.

Seven and two, two is in the bigger tree so seven goes below.

Six and one they're in equal size trees.

And seven and three, three is in the smaller tree so it goes below.

So, the weighted algorithm always makes sure that the smaller tree goes below.

And again, we wind up with a single tree representing all the objects.

But this time, we h ave some guarantee that no item is too far from the root and we'll talk about that explicitly in a second.

So, here's an example that shows the effect of doing the weighted quick union where we always put the smaller tree down below for the same set of union commands.

This is with a hundred sites and 88 union operations.

You can see in the top the big tree has some trees, some nodes, a fair distance from the root.

In the bottom, for the weighted algorithm all the nodes are within distance four from the root.

The average distance to the root is much, much lower.

Let's look at the Java implementation and then we'll look in more detail at, at that quantitative information.

So, we used the same data structure except, now we need an extra array, that for each item, gives the number of objects in the tree routed at that item.

That will maintain in the union operation.

Find implementation is identical to for quick union, you're just checking whether the roots are equal.

For the union implementation, we're going to modify the code to check the sizes.

And link the root of the smaller tree to the root of the larger tree in each case.

And then after changing the id link, we also change the size array.

If we make id, i a child of j, then we have to increment the size of j's tree by the size of i's tree.

Or if we do the other way around, then we have to increment the size of i's tree by the size of j's tree.

So, that's the full code in white for implementing quick union.

So, not very much code but much, much better performance.

In fact we can analyze the running time mathematically and show that defined operation, it takes time proportional to how far down the trees are in the node in the tree, the nodes are in the tree, but we can show that it's guaranteed that the depth of any node in the tree is at most the logarithm to the base two of N.

We use the notation Lg always for logarithm to the base two.

And, and, so for, if N is a thousand, that's going to be ten, if N is a million that's twenty, if N is a billion that's 30.

It's a very small number compared to N.

So, let's look at the proof of that.

We do some mathematical proofs in, in this course when they're critical such as this one.

And why is it true that the depth of any node x is, at most, log base two of N? Well, the key to understanding that is to, take a look at exactly when does the depth of any node increase? When does it go down further in the tree? Well.

The x's depth will increase by one, when its tree, T1 in this diagram, is merged into some other tree, T2 in this diagram.

Well, at that point we said we only do that if the size of T2 was bigger than the or equal to size of T1.

So, when the depth of x increases, the size of its tree at least doubles.

So, that's the key because that means that the size of the tree containing x can double at most log N times because if you start with one and double log N times, you get N and there's only N nodes in the tree.

So, that's a sketch of a proof that the depth of any node x is at most log base two of N.

And that has profound impact on the performance of this algorithm.

Now instead of the initialization always takes time proportional to N.

But now, both the union and the connected or find operation takes time proportional to log base two of N.

And that is an algorithm that scales.

If N grows from a million to a billion, that cost goes from twenty to 30, which is quite not acceptable.

Now, this was very easy to implement and, and we could stop but usually, what happens in the design of algorithms is now that we understand what it is that gains performance, we take a look and see, well, could we improve it even further.

And in this case, it's very easy to improve it much, much more.

And that's the idea of path compression.

And this idea is that, well, when we're trying to find the root of the tree containing a, a given node.

We're touching all the nodes on the path from that node to the root.

While we're doi ng that we might as well make each one of those just point to the root.

There's no reason not to.

So when we're looking, we're trying to find the root of, of P.

After we find it, we might as well just go back and make every node on that path just point to the root.

That's going to be a constant extra cost.

We went up the path once to find the root.

Now, we'll go up again to just flatten the tree out.

And the reason would be, no reason not to do that.

We had one line of code to flatten the tree, amazingly.

Actually to make a one liner code, we use a, a simple variant where we make every other node in the path point to its grandparent on the way up the tree.

Now, that's not quite as good as totally flattening actually in practice that it actually is just about as good.

So, with one line of code, we can keep the trees almost completely flat.

Now, this algorithm people discovered rather early on after figuring out the weighting and it turns out to be fascinating to analyze quite beyond our scope.

But we mentioned this example to illustrate how even a simple algorithmah, can have interesting and complex analysis.

And what was proved by Hopcroft Ulman and Tarjan was that if you have N objects, any sequence of M union and find operations will touch the array at most a c (N + M lg star N) times.

And now, lg N is kind of a funny function.

It's the number of times you have to take the log of N to get one.

And the way to think, it's called the iterated log function.

And in the real world, it's best to think of that as a number less than five because lg two^ 65536 is five.

So, that means that the running time of weighted quick union with path compression is going be linear in the real world and actually could be improved to even a more interesting function called the Ackermann function, which is even more slowly growing than lg<i>.

And another point about this is it< /i> seems that this is</i> so close to being linear that is t ime proportional to N instead of time proportional to N times the slowly growing function in N.

Is there a simple algorithm that is linear? And people, looked for a long time for that, and actually it works out to be the case that we can prove that there is no such algorithm.

So, there's a lot of theory that goes behind the algorithms that we use.

And it's important for us to know that theory and that will help us decide how to choose which algorithms we're going to use in practice, and where to concentrate our effort in trying to find better algorithms.

It's amazing fact that was eventually proved by Friedman and Sachs, that there is no linear time algorithm for the union find problem.

But weighted quick union with path compression in practice is, is close enough that it's going to enable the solution of huge problems.

So, that's our summary for algorithms for solving the dynamic connectivity problem.

With using weighted quick union and with path compression, we can solve problems that could not otherwise be addressed.

For example, if you have a billion operations and a billion objects I said before it might take thirty years.

We can do it in six seconds.

Now, and what's most important to recognize about this is that its the algorithm design that enables the solution to the problem.

A faster computer wouldn't help much.

You could spend millions on a super computer, and maybe you could get it done in six years instead of 30, or in two months but with a fast logarithm, you can do it in seconds, in seconds on your own PC.Okay.

So, we've looked at the quick union and quick find algorithms.

Both of which are easy to implement.

But simply can't support a huge dynamic connectivity problems.

So, how are we going to do better? That's what we'll look at next.

A very effective improvement, it's called weighting.

And it might have occurred to you while we are looking at these algorithms.

The idea is to when implementing the quick union algorithm take steps to avoid having tall trees.

If you've got a large tree and a small tree to combine together what you want to try to do is avoid putting the large tree lower, that's going to lead to long tall trees.

And there's a relatively easy way to do that.

What we'll do is we'll keep track of the number of objects in each tree and then, we'll maintain balance by always making sure that we link the root of the smaller tree to the root of the larger tree.

So, we, we avoid this first situation here where we put the larger tree lower.

In the weighted algorithm, we always put the smaller tree lower.

How we, let's see how we implement that.

Let's see a demo first.

Okay, so again start out in our normal starting position, where everybody's in their own tree.

And for when there's only two items to link it, it works, works the same way as before.

But now, when we have eight to merge with four and three, we put the eight as the child, no matter which order their arguments came, because it's the smaller tree.

So, six and five doesn't matter, whichever one goes down doesn't matter.

Nine and four, so now, nine is the small one four is the big one.

So, nine is going to be the one that goes down below.

Two and one, five and zero.

So now, five and zero five is in the bigger tree so zero goes below.

Seven and two, two is in the bigger tree so seven goes below.

Six and one they're in equal size trees.

And seven and three, three is in the smaller tree so it goes below.

So, the weighted algorithm always makes sure that the smaller tree goes below.

And again, we wind up with a single tree representing all the objects.

But this time, we h ave some guarantee that no item is too far from the root and we'll talk about that explicitly in a second.

So, here's an example that shows the effect of doing the weighted quick union where we always put the smaller tree down below for the same set of union commands.

This is with a hundred sites and 88 union operations.

You can see in the top the big tree has some trees, some nodes, a fair distance from the root.

In the bottom, for the weighted algorithm all the nodes are within distance four from the root.

The average distance to the root is much, much lower.

Let's look at the Java implementation and then we'll look in more detail at, at that quantitative information.

So, we used the same data structure except, now we need an extra array, that for each item, gives the number of objects in the tree routed at that item.

That will maintain in the union operation.

Find implementation is identical to for quick union, you're just checking whether the roots are equal.

For the union implementation, we're going to modify the code to check the sizes.

And link the root of the smaller tree to the root of the larger tree in each case.

And then after changing the id link, we also change the size array.

If we make id, i a child of j, then we have to increment the size of j's tree by the size of i's tree.

Or if we do the other way around, then we have to increment the size of i's tree by the size of j's tree.

So, that's the full code in white for implementing quick union.

So, not very much code but much, much better performance.

In fact we can analyze the running time mathematically and show that defined operation, it takes time proportional to how far down the trees are in the node in the tree, the nodes are in the tree, but we can show that it's guaranteed that the depth of any node in the tree is at most the logarithm to the base two of N.

We use the notation Lg always for logarithm to the base two.

And, and, so for, if N is a thousand, that's going to be ten, if N is a million that's twenty, if N is a billion that's 30.

It's a very small number compared to N.

So, let's look at the proof of that.

We do some mathematical proofs in, in this course when they're critical such as this one.

And why is it true that the depth of any node x is, at most, log base two of N? Well, the key to understanding that is to, take a look at exactly when does the depth of any node increase? When does it go down further in the tree? Well.

The x's depth will increase by one, when its tree, T1 in this diagram, is merged into some other tree, T2 in this diagram.

Well, at that point we said we only do that if the size of T2 was bigger than the or equal to size of T1.

So, when the depth of x increases, the size of its tree at least doubles.

So, that's the key because that means that the size of the tree containing x can double at most log N times because if you start with one and double log N times, you get N and there's only N nodes in the tree.

So, that's a sketch of a proof that the depth of any node x is at most log base two of N.

And that has profound impact on the performance of this algorithm.

Now instead of the initialization always takes time proportional to N.

But now, both the union and the connected or find operation takes time proportional to log base two of N.

And that is an algorithm that scales.

If N grows from a million to a billion, that cost goes from twenty to 30, which is quite not acceptable.

Now, this was very easy to implement and, and we could stop but usually, what happens in the design of algorithms is now that we understand what it is that gains performance, we take a look and see, well, could we improve it even further.

And in this case, it's very easy to improve it much, much more.

And that's the idea of path compression.

And this idea is that, well, when we're trying to find the root of the tree containing a, a given node.

We're touching all the nodes on the path from that node to the root.

While we're doi ng that we might as well make each one of those just point to the root.

There's no reason not to.

So when we're looking, we're trying to find the root of, of P.

After we find it, we might as well just go back and make every node on that path just point to the root.

That's going to be a constant extra cost.

We went up the path once to find the root.

Now, we'll go up again to just flatten the tree out.

And the reason would be, no reason not to do that.

We had one line of code to flatten the tree, amazingly.

Actually to make a one liner code, we use a, a simple variant where we make every other node in the path point to its grandparent on the way up the tree.

Now, that's not quite as good as totally flattening actually in practice that it actually is just about as good.

So, with one line of code, we can keep the trees almost completely flat.

Now, this algorithm people discovered rather early on after figuring out the weighting and it turns out to be fascinating to analyze quite beyond our scope.

But we mentioned this example to illustrate how even a simple algorithmah, can have interesting and complex analysis.

And what was proved by Hopcroft Ulman and Tarjan was that if you have N objects, any sequence of M union and find operations will touch the array at most a c (N + M lg star N) times.

And now, lg N is kind of a funny function.

It's the number of times you have to take the log of N to get one.

And the way to think, it's called the iterated log function.

And in the real world, it's best to think of that as a number less than five because lg two^ 65536 is five.

So, that means that the running time of weighted quick union with path compression is going be linear in the real world and actually could be improved to even a more interesting function called the Ackermann function, which is even more slowly growing than lg<i>.

And another point about this is it< /i> seems that this is</i> so close to being linear that is t ime proportional to N instead of time proportional to N times the slowly growing function in N.

Is there a simple algorithm that is linear? And people, looked for a long time for that, and actually it works out to be the case that we can prove that there is no such algorithm.

So, there's a lot of theory that goes behind the algorithms that we use.

And it's important for us to know that theory and that will help us decide how to choose which algorithms we're going to use in practice, and where to concentrate our effort in trying to find better algorithms.

It's amazing fact that was eventually proved by Friedman and Sachs, that there is no linear time algorithm for the union find problem.

But weighted quick union with path compression in practice is, is close enough that it's going to enable the solution of huge problems.

So, that's our summary for algorithms for solving the dynamic connectivity problem.

With using weighted quick union and with path compression, we can solve problems that could not otherwise be addressed.

For example, if you have a billion operations and a billion objects I said before it might take thirty years.

We can do it in six seconds.

Now, and what's most important to recognize about this is that its the algorithm design that enables the solution to the problem.

A faster computer wouldn't help much.

You could spend millions on a super computer, and maybe you could get it done in six years instead of 30, or in two months but with a fast logarithm, you can do it in seconds, in seconds on your own PC.


# C5_UnionFindApplications


Alright.

Now that we've seen efficient implementations of algorithms that can solve the unifying problem for huge problem instances let's look to see how that might be applied.

There's a huge number of applications of Union-find.

We talked about dynamic connectivity in networks there's many other examples in our computational infrastructure.

Down at the bottom is one of those important one is in image processing for understanding how to label areas in images.

We'll see later Kruskal's minimum spanning tree algorithm, which is a graph processing algorithm which uses Union-find as a subroutine.

There's algorithms in physics for understanding physical phenomenon that we'll look at an example and many others on this list.

So, the one we're going to talk about now is called percolation.

That's a model for many physical systems I'll give an abstract model and then just talk briefly about how it applies to physical systems.

So let's think of an n by n grid of squares that we call sites.

And we'll say that each site is open.

That's white in the diagram with probably P or blocked, that's black of the diagram with probability one - P and we define a system to, we say that a system is percolated if the top and the bottom are connected by open sites.

So the system at the left, you can find a way to get from the top to the bottom through white squares, but the system to the right does not percolate, there's no way to get from the top to the bottom through white squares.

So, that's a model for many systems.

You can think of for electricity.

You could think of a vacant site as being a conductor and, and a block site as being insulated.

And so if there's a conductor from top to bottom then the thing conducts electricity.

Or, you could think of it as, as water flowing through a porous substance of some kind.

Where a vacant side is just empty and a block side has got some material, and either the water flows through from top to bottom, or not.

Or you could think of a social network where it's people connected and either there's a c onnection between two people or not and these are a way not to get from one group of people to another communicating through that social network.

That's just a few examples of the percolation model.

So if we, we are talking abouta randomized model where the sites are vacant with the given probability.

And so it's pretty clear that if it's.

Probability that a site is vacant is low as on the left, two examples on the left in this diagram, it's not going to percolate.

There's not enough open site for there to be a connection from the top to the bottom.

If the probability is high and there is a lot of open sides, it definitely is going to percolate.

There would be lots of ways to get from the top to the bottom.

But in the middle, when it's medium, it's questionable whether it percolates or not.

So the scientific question, or the, mathematical question from this model is, how do we know, whether it's going to percolate or not? In this problem and in many similar problems, there's what's called a phase transition.

Which says that, you know, when it's low, it's not going to percolate.

When it's high, it is going to percolate.

And actually, the threshold between when it percolates and when it doesn't percolate is very sharp.

And actually there is a value as N gets large that if you're less than that value it almost certainly will not percolate, if you're greater it almost certainly will.

The question is what is that value.

This is an example of a mathematical model where the problem is, is very well articulated.

What's that threshold value but, nobody knows the solution to that mathematical problem.

The only solution we have comes from a computational model, where we run simulations to try and determine the value of that probability.

And those simulations are only enable by fast union find algorithms, that's our motivating example for why we might need fast union find algorithms, so let's look at that.

So what we're going to run is called a so called Monte Carlo simulation.

Where we initialize the whole grid to be block ed all black and then we randomly fill in open sites.

And we keep going.

And every time we add an open site, we check to see if it makes the system percolate.

And we keep going until we get to a point where the system percolates.

And we can show that the vacancy percentage at the time that it percolates is an estimate of this threshold value.

So what we want to do is run this experiment millions of times, which we can do in a computer, as long as we can, efficiently do the calculation of does it percolate or not.

That's a Monte Carlo simulation, a computational problem that gives us a solution to this, scientifc problem where, mathematical problems nobody knows how to solve yet.

So, let's, look in a little bit more detail of how we're going to use our dynam-, dynamic connectivity model to do this.

So, it's clear that, we'll create an object corresponding to each site.

And we'll give'em a name, from zero to N^2-1 as indicated here.

And then we'll connect them together.

If they're connected by open sites.

So the percolation model on the left corresponds to the, connection model on the right, according to what we've been doing.

Now, you might say, well, what we want to do is, connect, check whether any site in the bottom row is connected to any site in the top row, and use union find for that.

Problem with that is, that would be a brute force algorithm.

Would be quadratic, right on the face of it.

Because it would have N^2, calls to find, to check whether they're connected.

For each site on the top, I'd check each site on the bottom.

Much too slow.

Instead, what we do is create a virtual site on the top and on the bottom.

And then, when we want to know whether this system percolates, we just check whether the virtual top site is connected to the virtual bottom site.

So how do we model opening a new site? Well to open a site we just connect it to all it's adjacent open sites.

So that's a few calls to Union but that's easy to implement.

And then with that, simple, relationship we can use the exactly the code that we developed to go ahead and run a simulation for this connectivity problem.

And that's where we get the result that, by running enough simulations for a big-enough n, that this, percolation threshold is about .

92746.

With this fast algorithm we can get an accurate answer to the scientific question.

If we use a slow Union-find algorithm we won't be able to run it for very big problems and we won't get a very accurate answer.

So in summary, we took an important problem.

The, the dynamic connectivity problem.

We modeled the problem to try to understand precisely what kinds of data structures and algorithms we'd need to solve it.

We saw a few easy algorithms for solving the problem, and quickly saw that they were inadequate for addressing huge problems.

But then we saw how to improve them to get efficient algorithms.

And then left us with, applications that, could not be solved without these efficient algorithms.

All of this involves the scientific method.

For algorithm design where we try to develop mathematical models that help us understand the properties of the algorithms that we're developing.

And then we test those models through experimentation enabling us to improve algorithms iterating, developing better algorithms and more refined models until we get what we need to solve the practical problems that we have of interest.

That's going to be the overall architecture for studying algorithms that we're going to use throughout the course.
