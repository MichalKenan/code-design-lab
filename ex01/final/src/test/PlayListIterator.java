package test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Iterates over the song names of a playlist and its nested playlists in
 * depth-first order, preserving item order. Playlist names are never returned.
 *
 * Assumptions and chosen behavior:
 * - Playlists contain no cycles.
 * - The root playlist must not be null (NullPointerException otherwise).
 * - A playlist whose item array is null is treated as empty.
 * - Null items are skipped.
 * - Any non-null item that is not a PlayList is treated as a song; its name
 *   is returned as is, even if it is null.
 * - remove() is not supported (default Iterator behavior).
 */
public class PlayListIterator implements Iterator<String> {

	// Pending items; the top of the stack is the next item to visit.
	private final Deque<Item> stack = new ArrayDeque<>();

	public PlayListIterator(PlayList pl) {
		Objects.requireNonNull(pl, "playlist must not be null");
		stack.push(pl);
	}

	/**
	 * Expands playlists on top of the stack until a song is on top or the
	 * stack is empty. The song stays on the stack, so repeated calls are safe.
	 */
	@Override
	public boolean hasNext() {
		while (!stack.isEmpty()) {
			Item current = stack.peek();
			if (!(current instanceof PlayList))
				return true;

			stack.pop();
			Item[] items = ((PlayList) current).getItems();
			if (items == null)
				continue;
			// Push in reverse so the first item ends up on top.
			for (int i = items.length - 1; i >= 0; i--) {
				if (items[i] != null) // ArrayDeque rejects null
					stack.push(items[i]);
			}
		}
		return false;
	}

	@Override
	public String next() {
		if (!hasNext())
			throw new NoSuchElementException();
		return stack.pop().name;
	}
}
