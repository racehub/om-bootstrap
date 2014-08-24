# Contributing

Contributions to Om-Bootstrap are very welcome. (Thanks to the [Schema](https://github.com/prismatic/schema) project for the template for these contributing guidelines.)

Please file bug reports on [GitHub](https://github.com/racehub/om-bootstrap/issues).

For questions, feature requests, or discussion, please post on the Clojure [mailing list](https://groups.google.com/forum/#!forum/clojure).

Contributions are preferred as GitHub pull requests on topic branches. Please send pull requests to the `develop` branch. All new work is based off of `develop`; release are cut by merging `develop` into `master` and generating a release tag.

If you want to discuss a potential change before coding it up, please post on the mailing list or create a [GitHub issue](https://github.com/racehub/om-bootstrap/issues).

Om-Bootstrap is currently lacking heavy tests, but it does have a very solid example documentation site.  Before submitting a pull request, we ask that you:

 * please try to follow the conventions in the existing code, including standard Emacs indentation, no trailing whitespace, and a max width of 95 columns
 * rebase your feature branch on the latest develop branch
 * ensure any new code is well-tested, and if possible, any issue fixed is covered by one or more new tests
 * check that all of the tests pass
 * If you're adding a new component, add an example to the documentation site using the instructions below.

## Running the Tests

To run the ClojureScript tests, simply run `lein test` in the project root. Leiningen will build the relevant Clojurescript files and run all tests for you. You must have [PhantomJS](http://phantomjs.org/) installed for the tests to run.

## Writing a Documentation Example

The ClojureScript documentation site lives under `docs/src/cljs/om_bootstrap/docs.cljs`. Each type of component has its own section, called `*-block` (`button-block`, for example).

Each inlined example is generated from a `cljs` file located in the `dev/snippets` folder in the root of the project. Once a snippet is present, including `(->example (slurp-example <snippet-path>))` in a component will render the snippet in an "Example" box with the code accessible via a "show/hide code" toggle.

One example is the "active" button code, located at `dev/snippets/button/active.cljs`:

```clojure
#_
(:require [om-bootstrap.button :as b])

(b/toolbar {}
           (b/button {:bs-style "primary" :bs-size "large" :active? true}
                     "Primary button")
           (b/button {:bs-size "large" :bs-style "default" :active? true}
                     "Button"))
```

This is included in the documentation sit with the following call:

```clojure
(->example (slurp-example "button/active"))
```

The `dev/snippets` prefix, and the `.cljs` are left off.

Also, notice the `#_` reader macro before the import statement. That statement's not actually used! It's just there to provide guidance to the user. The `#_` gets stripped out when the code's displayed to the user under the "show/hide code" toggle. The rest of the snippet gets evaluated in the environment of `docs.cljs`, so you'll have to include new `require` aliases if you want to use other files in these snippets.
