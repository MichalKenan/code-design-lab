package test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Depth-first iterator over the song names of a playlist (Composite pattern).
 * Playlist names are never returned. Evaluation is lazy: a stack holds one
 * position per playlist on the current path, and the next song is located
 * only when hasNext() needs it.
 */
public class PlayListIterator implements Iterator<String> {

	private final Deque<Item[]> arrays = new ArrayDeque<>();
	private final Deque<Integer> positions = new ArrayDeque<>();
	private String nextSong; // null when not yet located or exhausted

	public PlayListIterator(PlayList pl) {
		if (pl != null)
			push(pl);
	}

	private void push(PlayList pl) {
		Item[] items = pl.getItems();
		if (items != null) {
			arrays.push(items);
			positions.push(0);
		}
	}

	/** Advances to the next song, if any; returns whether one was found. */
	private boolean advance() {
		while (nextSong == null && !arrays.isEmpty()) {
			Item[] items = arrays.peek();
			int pos = positions.pop();
			if (pos >= items.length) {
				arrays.pop(); // this playlist is finished; resume its parent
				continue;
			}
			positions.push(pos + 1);
			Item item = items[pos];
			if (item instanceof PlayList)
				push((PlayList) item);
			else if (item != null)
				nextSong = item.name;
		}
		return nextSong != null;
	}

	@Override
	public boolean hasNext() {
		return advance();
	}

	@Override
	public String next() {
		if (!advance())
			throw new NoSuchElementException();
		String result = nextSong;
		nextSong = null;
		return result;
	}
}
