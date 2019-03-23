package com.fscuat.mobiletrading;

    public class MSTradeAppConfig {

//        public static String address_server = "http://m.fss.com.vn/MFlexTest";
        //public static String address_server = "https://kbmobile.kbsec.com.vn/mobiapp"; // server that cu
//    public static String address_server = "httpPlacs://hftuat.fss.com.vn/MFlexTest";//uat
//    public static String address_server = "http://owa.msi.com.vn/MFlexApps";   // server uat moi

//       public static String address_server = "https://hftsso.fss.com.vn/MFlexTest";//test
//   public static String address_server = "http://192.168.18.190/MFlexApps";//PNS
        //   public static String address_server = "http://192.168.50.34/MFlexApps";//PNS-DBFNS


        /*Config Login SSO*/
//    link mở ssou hft
//    [6/28/2016 2:30:25 PM] FSS- giang xuan: hftsso.fss.com.vn/MFlexTest/Account/LoginOAth
//    [6/28/2016 2:30:35 PM] FSS- giang xuan: server gpush
//    https://hftsso.fss.com.vn/NTSServer
//    uat
//    public static String MobileServerUrl = "https://hftuat.fss.com.vn/MFlexTest/Account/LoginOAuth?lang=";
//    public static String returnLoginUrl = "https://hftuat.fss.com.vn/MFlexTest/";
//    public static String NTServerUrl = "https://hftuat.fss.com.vn/NTSServer";
        //    test
//    pblic static String MobileServerUrl = "https://hftsso.fss.com.vn/MFlexTest/Account/LoginOAuth?lang=";
//    public static String returnLoginUrl = "https://hftsso.fss.com.vn/MFlexTest/";
//    public static String NTServerUrl = "https://hftsso.fss.com.vn/NTSServer";
//    production
        public static String MobileServerUrl = "https://uat.vcbs.com.vn/GOLIVE_MflexApps//Account/LoginOAuth?lang=";
        public static String returnLoginUrl = "https://uat.vcbs.com.vn/GOLIVE_MflexApps/";
        public static String NTServerUrl = "https://uat.vcbs.com.vn/NTSServerTCSC";


        public static final String controller_FindCustodyCd = "/MAccount/FindCustodyCd";
        public static final String controller_ChooseAcctno = "/MAccount/ChooseAcctno";
        public static final String controller_checkSession = "/MAccount/checkSession";

        /* login */
        public static final String controller_ResetPass = "/MAccount/ResetPass";
        //    logoutSSO
        public static final String logout_SSO = "/Account/LogOff";
        //    get config server
        public static final String getConfigServer = "/MClientConfig/getConfigApp";
        /* market */
        public static final String controller_MarketIndex = "/MHome/Mkt";
        public static final String controller_MarketChart = "/MHome/MarketChart";
        /* watchlist */
        public static final String controller_GetAllWatchListDefault = "/MWatchList/GetAllWatchListDefault";
    public static final String controller_StockDetails = "/MWatchList/StockDetails";
    public static final String controller_GetDefaultWatchList = "/MWatchList/GetDefaultWatchList";
    public static final String controller_RmFrWL = "/MWatchList/RmFrWL";
    public static final String controller_GetWLSymbols = "/MWatchList/GetWLSymbols";
    /* balance */
    public static final String controller_GetMoneyInfo = "/MBalance/GetMoneyInfo";
    /* cashtransfer */
    public static final String controller_GetCashTransferInfo = "/MCash/getCashTransferInfo";
    public static final String controller_GetTransferFeeAndTotal = "/MCash/getTransferFeeAndTotal";
    public static final String controller_GetTransDesc = "/MBR/getTransDesc";

    /* account config */
    public static final String controller_ChangePasswordBroker = "/MAccount/ChangePasswordBroker";
    public static final String controller_ChangePassword = "/MAccount/ChangePassword";
    public static final String controller_ChangeTradingPassword = "/MAccount/ChangeTradingPassword";
    public static final String controller_ChangeTradingPasswordAndPin = "/MAccount/ChangePasswordAndPin";

    /* porfolio */
    public static final String controller_GetPorfolio = "/MStock/GetPortfolio";
    public static final String controller_GetFullPorfolio = "/MStock/GetFullPortfolio";

    /* contact */
    public static final String controller_ContactInfo = "/MAccount/ContactInfo";

    /* notification */
    public static final String controller_GetList = "/MNotification/getList";
    public static final String controller_GetUnRead = "/MNotification/getUnread";
    public static final String controller_NotifyDetail = "/MNotification/getDetail";

    /* Market */
    public static final String controller_JIndex = "/MHome/JIndex";

    /* News */
    public static final String controller_GetAllNew = "/MNews/GetAllNew";
    public static final String controller_GetNextNews = "/MNews/GetNextNews";
    public static final String controller_GetNewsDetail = "/MNews/GetNewsDetail";

    /* orderlist */
    public static final String controller_GTCOrders = "/MOrder/GTCOrders";

    /* Report */
    public static final String controller_GetDataSource = "/MBR/GetDataSource";
    public static final String controller_GetBRList = "/MBR/GetBRList";
    public static final String controller_Report = "/MBR/Report";

    /* past place order */
    public static final String controller_PastPlaceOrders = "/MOrder/PastPlaceOrders";
    /* Guarantee */
    public static final String controller_getGuaranteeList = "/MBR/getGuaranteeList";
    public static final String controller_getAllocateT0 = "/MBR/AllocateT0";
    public static final String controller_doGrantT0 = "/MBR/GrantT0";

    /* Place Order */
    //public static final String controller_CheckOrder = "/MTrade/CheckOrder";
    public static final String controller_CheckOrder = "/MTrade/Checkvalidorder";
    //public static final String controller_CheckGTCOrder = "/MTrade/CheckGTCOrder";
    public static final String controller_CheckGTCOrder = "/MTrade/CheckvalidGTCorder";
    public static final String controller_FindStock = "/MTrade/FindStock";
    public static final String controller_GetExchangebysymbol = "/MTrade/GetExchangebysymbol";
    public static final String controller_GetStocksByAffaccno = "/MStock/GetStocksByAffaccno";

    /*
     * config app
     */
    public static final String USERNAME_DEFAULT = "085";
    public static final String timeout_default = "180";
    public static final String MSBBankId = "302";
    public static final String VersionApp = "A.1.3.0";
    public static String link_PaymentGuide = null;
    public static String link_Guide = null;
    public static String link_Guide_Broker = null;

    public static String username_demo;
    public static String pass_demo;
    public static String pin_demo;


    public static String getCustomerInfo = "/MAccount/LoginOauth";
    public static String NTServerHubName = "NTSHub";

}
