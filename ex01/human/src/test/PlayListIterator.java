package test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Deque;
import java.util.ArrayDeque;

/**
 * An iterator for a PlayList that traverses over the names of songs in a playlist and its nested playlists
 * in depth-first order.
 */
public class PlayListIterator implements Iterator<String> {
	
	private final Deque<Item> stack;

	/**
	 * Constructs a new PlayListIterator for the given PlayList.
	 *
	 * @param pl the PlayList to iterate over
	 */
	public PlayListIterator(PlayList pl) {
		stack = new ArrayDeque<>();
        stack.push(pl);
	}

	/**
	 * Returns true if there are more songs to iterate over, false otherwise.
	 *
	 * @return true if there are more songs available, false otherwise
	 */
	@Override
	public boolean hasNext() {
		while (!stack.isEmpty()) {
			Item current = stack.peek();

			if (current instanceof Song) {
				// Keep the song on the stack so repeated hasNext() calls don't skip it.
				return true;
			}

			stack.pop();
			PlayList playlist = (PlayList) current;
			Item[] items = playlist.getItems();

			for (int i = items.length - 1; i >= 0; i--) {
				stack.push(items[i]);
			}
    	}

    	return false;
	}

	/**
	 * Returns the name of the next song in depth-first order.
	 *
	 * @return the name of the next song
	 * @throws NoSuchElementException if no songs remain
	 */
	@Override
	public String next() {
		if (!hasNext()) {
        throw new NoSuchElementException();
    	}

		Item song = stack.pop();
    	return song.name;
	}
	
}
