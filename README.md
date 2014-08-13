## Om-Bootstrap [![Build Status](https://secure.travis-ci.org/racehub/om-bootstrap.png)](http://travis-ci.org/racehub/om-bootstrap)

Here's the latest version:

[![Clojars Project](http://clojars.org/racehub/om-bootstrap/latest-version.svg)](http://clojars.org/racehub/om-bootstrap)

## Deploying to Heroku

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

## TODO before release:

- Make sure all required vs non-required properties are set!
- Wrap componentk schemas in `om-tools.schema/cursor`
- Verify types with schema tests

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
* Nav, NavItem (nav.cljs)
* Popover (random.cljs)
* Badge (random.cljs)

## In Progress

* Modal, ModalTrigger (modal.cljs)
* ProgressBar (progress-bar.cljs)
* Nav Collapsible Functionality

## Needed Components

* DropdownButton, SplitButton, MenuItem
* DropdownMenu (?)
* Subnav (?)
* Panel (hard), PanelGroup (easy), Accordion (easy)
* NavBar
* TabbedArea, TabPane
* Pager
* Carousel
* CarouselItem

## Mixins

* Listener (mixins.cljs)
* Timeout (mixins.cljs)

### Needed Mixins

* Fade
* Overlay
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

Om-Bootstrap is currently supported on Clojure 1.5.1 and 1.6.x and the latest version of ClojureScript. TODO: Note about supported Om and React versions.
