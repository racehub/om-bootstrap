## 0.2.6

- Upgraded Clojurescript dependency on the doc site to get around this bug: http://dev.clojure.org/jira/browse/CLJS-839. Added a note.

This hash code bug was causing `bs-class-set`'s internal lookup in `class-map` to fail in Safari 7.0.x once the JS JIT compiler kicked in.

## 0.2.5

- Removed in-progress fade listeners.
- Added glyphicon
- Lots more examples for the documentation site

## 0.2.0

- Added many components.
- Created documentation site
- Upgraded to Om 0.7.x and React 0.11

## 0.1.0

* Initial Release.
