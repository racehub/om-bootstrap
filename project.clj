(def server-deps
  '[[javax.servlet/servlet-api "2.5"]
    [compojure "1.1.8"]
    [http-kit "2.1.18"]
    [hiccup "1.0.5"]])

(defproject racehub/om-bootstrap "0.2.3-SNAPSHOT"
  :description "Bootstrap meets Om."
  :url "http://github.com/racehub/om-bootstrap"
  :license {:name "MIT Licens"
            :url "http://www.opensource.org/licenses/mit-license.php"
            :distribution :repo}
  :scm {:name "git"
        :url "https://github.com/racehub/om-bootstrap"}
  :min-lein-version "2.3.0"
  :uberjar-name "om-bootstrap.jar"
  :jar-exclusions [#".DS_Store"]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [prismatic/schema "0.2.4"
                  :exclude [org.clojure/clojurescript]]]
  :profiles {:provided
             {:dependencies [[org.clojure/clojurescript "0.0-2261"]
                             [prismatic/om-tools "0.3.2"]
                             [secretary "1.2.0"]
                             [weasel "0.3.0"]
                             [om "0.7.1"]]}
             :uberjar {:aot :all
                       :omit-source true
                       :main om-bootstrap.server
                       :plugins [[lein-cljsbuild "1.0.3"]]
                       :prep-tasks ^:replace [["clean"]
                                              ["cljsbuild" "clean"]
                                              ["cljsbuild" "once" "heroku"]
                                              ["javac"]
                                              ["compile" ":all"]]
                       :dependencies ~server-deps
                       :source-paths ["docs/src/clj"]
                       :resource-paths ["dev"]}
             :dev {:plugins [[lein-cljsbuild "1.0.3"]
                             [com.cemerick/clojurescript.test "0.3.0"]
                             [paddleguru/lein-gitflow "0.1.2"]]
                   :dependencies ~(conj server-deps '[com.cemerick/piggieback "0.1.3"])
                   :source-paths ["docs/src/clj" "docs/src-dev"]
                   :resource-paths ["dev"]
                   :main om-bootstrap.server
                   :repl-options
                   {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}
  :aliases {"test" ["cljsbuild" "test"]
            "repl" ["do" "cljsbuild" "once" "docs," "repl"]}
  :cljsbuild
  {:test-commands {"unit"
                   ["phantomjs" :runner
                    "test/vendor/es5-shim.js"
                    "test/vendor/es5-sham.js"
                    "test/vendor/console-polyfill.js"
                    "this.literal_js_was_evaluated=true"
                    "target/om_bootstrap.js"]}
   :builds
   {:docs
    {:source-paths ["src" "docs/src/cljs" "docs/src/clj"]
     :compiler {:output-to "dev/public/assets/main.js"
                :output-dir "dev/public/generated"
                :optimizations :none
                :source-maps true}}
    :heroku
    {:source-paths ["src" "docs/src/cljs" "docs/src/clj"]
     :compiler {:output-to "dev/public/assets/generated/om_bootstrap.js"
                :output-dir "dev/public/assets/generated"
                :externs ["react/externs/react.js"
                          "externs/highlight.js"]
                :preamble ["react/react.min.js"]
                :optimizations :advanced
                :pretty-print false
                :source-map "dev/public/assets/generated/om_bootstrap.js.map"}}
    :test
    {:source-paths ["src" "test"]
     :compiler {:output-to "target/om_bootstrap.js"
                :optimizations :whitespace
                :pretty-print true
                :preamble ["react/react.min.js"]
                :externs ["react/externs/react.js"]}}}}
  :lein-release {:deploy-via :shell
                 :shell ["lein" "deploy" "clojars"]})
