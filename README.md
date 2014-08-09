## Om-Bootstrap [![Build Status](https://secure.travis-ci.org/racehub/om-bootstrap.png)](http://travis-ci.org/racehub/om-bootstrap)

## TODO before release:

- Make sure all required vs non-required properties are set!
- Wrap componentk schemas in `om-tools.schema/cursor`
- Verify types with tests.
- Add the clojars pill to the README.

## Components

* Button, ButtonGroup, Toolbar (button.cljs)
* Input (input.cljs)
* Jumbotron (random.cljs)
* Label (random.cljs)
* Well (random.cljs)
* PageHeader (random.cljs)
* Grid, Row, Col (grid.cljs)
* ToolTip (random.cljs, except for examples)
* Alert (random.cljs, except for examples)

## In Progress

* Modal, ModalTrigger (modal.cljs)
* ProgressBar (progress-bar.cljs)

## Needed Components

* DropdownButton, SplitButton, MenuItem
* DropdownMenu (?)
* Subnav (?)
* Panel (hard), PanelGroup (easy), Accordion (easy)
* Popover (easy)
* Nav, NavItem, NavBar
* TabbedArea, TabPane
* Pager
* Carousel
* CarouselItem
* Badge
* Use Input as a wrapper (easy):

```
If type is not set, child element(s) will be rendered instead of an input
element.getValue() will not work when used this way.
```

## Mixins

* Fade
* Overlay
* Listener (mixins.cljs)
* Timeout (mixins.cljs)

### Needed Mixins

* DropdownStateMixin
* CollapsibleMixin

## ClojureScript Repl

`om-bootstrap` comes with a development environment you can use to hack on the demo project. to start the Clojurescript repl,

```clojure
lein repl
```

Then, at the repl, run `(start-repl)` to fire it up. Navigating to the docs site will connect to the repl automatically and you'll be all set.

## Community

TODO: Note on joining the Clojure mailing list.

For announcements of new releases, you can also follow on [@RaceHubHQ](http://twitter.com/RaceHubHQ) on Twitter.

We welcome contributions in the form of bug reports and pull requests; please see `CONTRIBUTING.md` in the repo root for guidelines.

## Supported Clojure versions

Om-Bootstrap is currently supported on 1.5.1 and 1.6.x and the latest version of ClojureScript. TODO: Note about supported Om and React versions.
