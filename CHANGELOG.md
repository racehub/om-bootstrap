## 0.2.6

- Upgraded Clojurescript dependency on the doc site to get around this bug: http://dev.clojure.org/jira/browse/CLJS-839. Added a note.

This hash code bug was causing `bs-class-set`'s internal lookup in `class-map` to fail in Safari 7.0.x once the JS JIT compiler kicked in.

From https://github.com/racehub/om-bootstrap/pull/13:
- `om-bootstrap.util/clone-with-props` can now "clone" proper om components by injecting extra attributes into the om cursor.
- `:on-select` handlers on top level nav elements now get called if set, along with the current nav-item `:on-select` handlers

### New Components
- dropdown-mixin (mixins.cljs)
- menu-item, dropdown-menu, dropdown (button.cljs)

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
