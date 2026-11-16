# Reflection

## What I expected
Before asking the AI to solve the exercise independently, I expected it to use depth-first traversal and a stack, similar to the approach I had chosen. A stack seemed natural because the iterator needs to enter nested playlists and remember where to continue after finishing them.

I expected both solutions to face the same challenge: repeated calls to hasNext() should not skip a song, and next() should return the next song in the required order. I also expected differences in how the code was organized and how edge cases were handled.

## What actually happened
The first solution used a stack of items that still needed to be processed. When the iterator encountered a playlist, it added its items to the stack in reverse order. When it found a song, it left the song at the top of the stack until next() was called.

AI's solution also used a stack-based depth-first traversal, but it used two stacks: one for item arrays and another for the current position in each array. It also stored the next song's name in a separate field.

Comparing the solutions showed me that the same traversal can be implemented while keeping track of its state in different ways. I also noticed differences in how the solutions handled null values.

## Most important difference
The most important difference was how the two implementations stored the state of the depth-first traversal.

My first solution used one stack containing items that still needed to be processed. When it encountered a playlist, it pushed its children onto the stack in reverse order. This ensured that the first child would be processed first.

AI's solution used two stacks to keep track of the current path: one stored the item arrays of playlists being traversed, and the other stored the next position in each array. Instead of pushing all the children of a playlist onto the stack, it used these positions to know where to continue after returning from a nested playlist.

Both approaches preserve the required traversal order, but they manage the remaining work differently. I found the single-stack approach easier to follow, while the two-stack approach avoids pushing every child onto the stack at once.

## What I learned
I learned that two solutions can produce the same depth-first traversal while making different trade-offs in how they store its state.

My single-stack approach was easier for me to read and reason about: the stack contains the items that still need to be processed. However, when the iterator encounters a playlist, it pushes all of that playlist's children onto the stack. For a playlist with many items, this can leave many pending items on the stack.

AI's approach keeps track of the current position in each playlist on the traversal path instead of pushing all its children at once. This can reduce the number of pending entries stored for a wide playlist, but it also introduces two stacks that must remain coordinated.

I had not considered this trade-off before comparing the implementations. The exercise helped me understand that readability and memory usage are both relevant when choosing a design, and that the better choice depends on the expected input and the importance of keeping the implementation simple.

## What I changed
For the final version, I chose to keep the single-stack approach from my first implementation because I found it simpler to understand and explain. I did not adopt AI's two-stack design, but comparing the solutions made me reconsider how my implementation handled null values.

I updated the final version to treat a null item array as an empty playlist and to skip null items before adding them to the stack. I also chose to reject a null root playlist with a clear exception. These behaviors were not specified in the exercise, so they are explicit design decisions for the final implementation.

The iterator still returns a song's name as provided, including a null name, and preserves the required depth-first order and iterator behavior.

## What I would do next time
Next time I work on a software design problem, I would consider more than one approach before committing to an implementation. I would compare the alternatives based on their trade-offs, such as readability, complexity, memory usage, and the expected input.

I would also identify unclear requirements and edge cases earlier, rather than discovering them only after comparing implementations. I would make my assumptions explicit and design tests that check both the expected behavior and the contract of the interfaces I use.

I would use the comparison between different solutions to look for design considerations I may not have noticed initially. This would help me make more informed decisions and explain the trade-offs behind the approach I choose.

## Exercise-specific insight
The most significant difference I noticed in the iterator contract was how each implementation kept track of the next song between calls to hasNext() and next().

In my first solution, hasNext() leaves the next song at the top of the stack. Repeated calls to hasNext() therefore see the same song, and next() removes it from the stack and returns its name.

In AI's solution, the traversal position can advance while hasNext() searches for a song. The song's name is then stored in a separate field until next() returns it. Repeated calls to hasNext() use the stored name rather than advancing again.

This comparison helped me understand that an iterator may update its internal state during hasNext(), as long as its observable behavior remains consistent and it does not skip an item.