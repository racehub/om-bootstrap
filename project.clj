(defproject racehub/om-bootstrap "0.2.0-SNAPSHOT"
  :description "Bootstrap meets Om."
  :url "http://github.com/racehub/om-bootstrap"
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo}
  :scm {:name "git"
        :url "https://github.com/racehub/om-bootstrap"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [prismatic/schema "0.2.4"]]
  :plugins [[lein-cljsbuild "1.0.3"]
            [com.cemerick/clojurescript.test "0.3.0"]]
  :profiles {:provided
             {:dependencies [[org.clojure/clojurescript "0.0-2261"]
                             [prismatic/om-tools "0.3.0"]
                             [om "0.7.1"]]}
             :dev {:dependencies [[prismatic/dommy "0.1.2"]]
                   :plugins [[paddleguru/lein-gitflow "0.1.2"]]
                   :cljsbuild
                   {:builds
                    [{:id "docs"
                      :source-paths ["src" "docs/src"]
                      :compiler {:output-to "docs/main.js"
                                 :output-dir "docs/out"
                                 :optimizations :none}}]}}}
  :lein-release {:deploy-via :shell
                 :shell ["lein" "deploy" "clojars"]})
