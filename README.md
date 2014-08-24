## Om-Bootstrap [![Build Status](https://secure.travis-ci.org/racehub/om-bootstrap.png)](http://travis-ci.org/racehub/om-bootstrap)

A ClojureScript library of [Bootstrap 3](http://getbootstrap.com) components built on top of [Om](https://github.com/swannodette/om). See the [documentation site](http://om-bootstrap.herokuapp.com/) for a ton of usage examples.

Here's the latest Leiningen version info:

[![Clojars Project](http://clojars.org/racehub/om-bootstrap/latest-version.svg)](http://clojars.org/racehub/om-bootstrap)

**This is an alpha release. The API and organizational structure are
subject to change. Comments and contributions are much appreciated.**

## Bootstrap Components

This project's goal is to provide wrappers for all Bootstrap 3 components, active or inactive, so they can be used easily in [Om](https://github.com/swannodette/om) / ClojureScript projects.

Om-Bootstrap's [documentation site](http://om-bootstrap.herokuapp.com/) has usage examples for all components that exist so far. The following components are currently complete:

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

## Mixins

The project contains a few mixins that help in writing active Om components. THe current set of mixins makes it easy to set listeners and timeouts on some component, and guarantee that they'll be cleaned up when the component unmounts:

* Listener (mixins.cljs)
* Timeout (mixins.cljs)

## Components In Progress

* Modal, ModalTrigger (modal.cljs)
* ProgressBar (progress_bar.cljs)
* Nav Collapsible Functionality

## Needed Components

These, and the mixins below, are the project's biggest TODOs.

* DropdownButton, SplitButton, MenuItem
* DropdownMenu (?)
* Subnav (?)
* Panel (hard), PanelGroup (easy), Accordion (easy)
* NavBar
* TabbedArea, TabPane
* Pager
* Carousel
* CarouselItem

### Needed Mixins

* FadeMixin
* Overlay
* DropdownStateMixin
* CollapsibleMixin

## ClojureScript Repl

`om-bootstrap` comes with a development environment you can use to hack on the demo project. First, start the repl with:

```clojure
lein repl
```

You'll be dropped into the `om-bootstrap.server` namespace. Fire up the webapp by running `(-main)`. You can access the dev site at `http://localhost:8080`. (Set the `PORT` environment variable to customize the launch port.)

Next, require the `repl` namespace and boot the Clojurescript repl:

```clojure
(require '[om-bootstrap.repl :as repl])
(repl/repl!)
```

This will start a Websocket repl using [Weasel](https://github.com/tomjakubowski/weasel). When you reload `http://localhost:8080`, it should automatically connect to Weasel and anything you type at the repl will start evaluating.

I personally like to start the repl with `lein repl :headless` and do all of this from Emacs. Whatever floats your boat.

## Supported Clojure versions

Om-Bootstrap works with the following dependencies:

- Clojure 1.6.x
- React.JS 0.11.x
- Om 0.7.x
- Bootstrap 3.1.x (probably works on 3.2, haven't tested it)

and the latest version of ClojureScript. Please create a [GitHub issue](https://github.com/racehub/om-bootstrap/issues) if you run into problems with these versions or would like to see further versions supported.

## Authors

- Sam Ritchie <https://twitter.com/sritchie>
- Dave Petrovics <https://github.com/dpetrovics>

We'd love to add your name to this list! See `CONTRIBUTING.md` for information on how to help out.

## Community and Contributions

This project may grow large enough for its own mailing list someday, but for now please feel free to join the Clojure [mailing list](https://groups.google.com/forum/#!forum/clojure) to ask questions or discuss how you're using Om-Bootstrap.

For announcements of new releases, you can follow [@RaceHubHQ](http://twitter.com/RaceHubHQ) on Twitter.

We welcome contributions in the form of bug reports and pull requests! Please see `CONTRIBUTING.md` in the repo root for guidelines.

## Running the Tests

To run the ClojureScript tests, simply run `lein test` in the project root. Leiningen will build the relevant Clojurescript files and run all tests for you. You must have [PhantomJS](http://phantomjs.org/) installed for the tests to run.

## Deploying to Heroku

If you fork this project and would like to deploy a version of the documentation site to Heroku to show off, all you need to do is click this button:

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

And boom! Heroku will guide you through the process of deploying a fresh copy of `http://om-bootstrap.herokuapp.com`.

## Acknowledgements

HUGE thanks to the [react-bootstrap](https://github.com/react-bootstrap/react-bootstrap) project, which I used as a reference when creating all components and the documentation site. I've kept their original MIT license on the project for now with their names, since I'm not really sure how to license a "fork" that contains no copied code, just lifted CSS from the documentation site.
