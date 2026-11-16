# AI Use

## Tool / Model
Claude Code (VS Code extension), model Claude Sonnet 5.5 (`claude-sonnet-5-5`).

## Role
Claude implemented `PlayListIterator` independently (the "AI version" in `ex01/ai/`). I (the student) supplied the task and boundaries and did not write this version's code.

## Main prompt(s)
"Implement exercise 1 independently in a fresh context." The prompt included the exercise requirements (`PlayListIterator` as an `Iterator<String>`, depth-first, song names only, order preserved, `package test`). It told Claude to inspect only the supplied exercise files, not to read or use `ex01/human/`, `ex01/final/` or any other completed solution, and not to modify the support classes. It asked for the implementation under `ex01/ai/`, compiling and running with `MainTrain`, edge-case checks, and this document. It did not ask for the final solution, the reflection, commits or pushes.

## Inputs provided
- Exercise description (pasted in the prompt)
- `Item.java`, `Song.java`, `PlayList.java`, `MainTrain.java` and the unimplemented `PlayListIterator.java` stub, read from `ex01/ai/src/test/` and `C:\Users\micha\Downloads\ex1`
- Claude also read this file's template and listed the `ex01` folder names. It did not open anything in `human/` or `final/`.

## Context
Fresh context: yes (the session started with this prompt and no earlier conversation).

## Notes
- Result: `ex01/ai/src/test/PlayListIterator.java`. It is a lazy iterator with an explicit stack of (item array, position) pairs. It does not use recursion or pre-flatten the list.
- Verification: the supplied `MainTrain` printed only `done` (no failures). A throwaway edge-case test, kept outside the repo, also passed. It covered empty playlists, nested empty playlists, `null` item arrays and `null` elements, repeated `hasNext()`, `next()` without `hasNext()`, `NoSuchElementException` when exhausted, `remove()` throwing `UnsupportedOperationException`, and a 50,000-level nesting depth.
- Limitations: no cycle detection, which the exercise says to assume away. The iterator is not safe if the playlist's items are modified during iteration.
- There is a stray stub at `ex01/ai/src/PlayListIterator.java` (outside `test/`) that I did not touch. It duplicates the class name, so remove it or exclude it from the build.
