package vn.finance.app.pages.routing

sealed class RootEnum {
    data object OnBoarding : RootEnum()
    data object Login : RootEnum()
    data object Home : RootEnum()
}
