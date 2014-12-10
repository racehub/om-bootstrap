## 0.3.2 (12/9/2014)

- Added `href` tags to all `Show Code` links. Without these they weren't expanding on mobile browsers (https://github.com/racehub/om-bootstrap/issues/34)
- Fixed compatibility issue with om-0.8.0-beta3, and added tests.
- Upgraded clojure to 1.7.0-alpha2
- Upgraded core.async to 0.1.346.0-17112a-alpha
- Upgraded om-tools to 0.3.6
- Upgraded schema to 0.3.1
- Upgraded weasel to 0.4.2

## 0.3.1 (10/15/2014)

- Revert schema upgrade... fancy that :)

## 0.3.0 (9/25/2014)

- Upgrade to Schema 0.3.0

## 0.2.9 (9/20/2014)

- Relaxes a bunch of required keys in schemas back to optional: https://github.com/racehub/om-bootstrap/pull/23
- Converts `om-bootstrap.button/menu-item` to an Om component, so that a wrapping `dropdown` component can access its `:on-select` attribute. The interface stays the same: https://github.com/racehub/om-bootstrap/pull/25

## 0.2.8

- Add `:list-group` to panels, makes body optional: https://github.com/racehub/om-bootstrap/pull/22 (@brutasse)

## 0.2.7

- Added a bunch of tests to the `types` and `util` namespaces.
- Don't nuke merged :refs: https://github.com/racehub/om-bootstrap/pull/20 (@pyr)

## 0.2.6

- Upgraded Clojurescript dependency on the doc site to get around this bug: http://dev.clojure.org/jira/browse/CLJS-839. Added a note.

This hash code bug was causing `bs-class-set`'s internal lookup in `class-map` to fail in Safari 7.0.x once the JS JIT compiler kicked in.

From https://github.com/racehub/om-bootstrap/pull/13:
- `om-bootstrap.util/clone-with-props` can now "clone" proper om components by injecting extra attributes into the om cursor.
- `:on-select` handlers on top level nav elements now get called if set, along with the current nav-item `:on-select` handlers

### New Components

- `dropdown-mixin` (mixins.cljs)
- `menu-item`, `dropdown-menu`, `dropdown` (button.cljs)
- `split` (ie, SplitButton) (button.cljs)
- `navbar` (ie, SplitButton) (button.cljs)

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
