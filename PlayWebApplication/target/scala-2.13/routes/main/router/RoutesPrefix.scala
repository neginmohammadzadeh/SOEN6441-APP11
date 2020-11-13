// @GENERATOR:play-routes-compiler
// @SOURCE:D:/uni Projects/play/SOEN6441-APP/PlayWebApplication/conf/routes
// @DATE:Sat Nov 14 02:29:13 IRST 2020


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
