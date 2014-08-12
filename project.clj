(defproject racehub/om-bootstrap "0.2.1-SNAPSHOT"
  :description "Bootstrap meets Om."
  :url "http://github.com/racehub/om-bootstrap"
  :license {:name "MIT Licens"
            :url "http://www.opensource.org/licenses/mit-license.php"
            :distribution :repo}
  :scm {:name "git"
        :url "https://github.com/racehub/om-bootstrap"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [prismatic/schema "0.2.4"]]
  :plugins [[lein-cljsbuild "1.0.3"]
            [com.cemerick/clojurescript.test "0.3.0"]]
  :profiles {:provided
             {:dependencies [[org.clojure/clojurescript "0.0-2261"]
                             [prismatic/om-tools "0.3.0"]
                             [om "0.7.1"]]}
             :dev {:dependencies [[com.cemerick/piggieback "0.1.3"]
                                  ;; See https://groups.google.com/forum/#!topic/compojure/GyPAwLQcjzY
                                  [javax.servlet/servlet-api "2.5"]
                                  [prismatic/dommy "0.1.2"]
                                  [compojure "1.1.8"]
                                  [http-kit "2.1.18"]
                                  [secretary "1.2.0"]
                                  [weasel "0.3.0"]]
                   :source-paths ["docs/src/clj"]
                   :resource-paths ["dev"]
                   :main om-bootstrap.server
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :plugins [[paddleguru/lein-gitflow "0.1.2"]]
                   :cljsbuild
                   {:builds
                    [{:id "docs"
                      :source-paths ["src" "docs/src/cljs"]
                      :compiler {:output-to "dev/public/assets/main.js"
                                 :output-dir "dev/public/generated"
                                 :optimizations :none
                                 :source-maps true}}]}}}
  :lein-release {:deploy-via :shell
                 :shell ["lein" "deploy" "clojars"]})
