package com.fss.mobiletrading.function;

import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;

public class MenuFunction extends AbstractFragment {

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub
		
	}
//	public static final int BANG_GIA = 1;
//	public static final int BAO_CAO_MOI_GIOI = 40;
//	public static final int BO_LOC_CANH_BAO = 32;
//	public static final int BO_LOC_CO_PHIEU = 31;
//	public static final int BO_LOC_VA_CANH_BAO = 30;
//	public static final int CHON_TIEU_KHOAN = 39;
//	public static final int STOCK_TRANSFER = 28;
//	public static final int CASH_TRANSFER = 19;
//	public static final int INTERNAL_CASH_TRANSFER = 20;
//	public static final int BANK_CASH_TRANSFER = 22;
//	public static final int STOCK_COMPANY_CASH_TRANSFER = 21;
//	public static final int INTERNAL_CASH_TRANSFER_REGISTER = 23;
//	public static final int SC_CASH_TRANSFER_REGISTER = 24;
//	public static final int BANK_CASH_TRANSFER_REGISTER = 25;
//	public static final int RIGHT_OFF_REGISTER = 29;
//	public static final int DANG_XUAT = 38;
//	public static final int PORFOLIO = 11;
//	public static final int DAT_LENH = 0;
//	public static final int ACCOUNT_CONFIG = 34;
//	public static final int DOI_MAT_KHAU_GIAO_DICH = 35;
//	public static final int OTHER_TRADE = 26;
//	public static final int HE_THONG_VA_TAI_KHOAN = 33;
//	public static final int CASH_TRANSFER_HISTORY = 16;
//	public static final int LICH_SU_DANG_KY_QUYEN_MUA = 17;
//	public static final int LICH_SU_KIEN = 5;
//	public static final int ORDER_HISTORY = 15;
//	public static final int ADVANCE_HISTORY = 18;
//	public static final int NGON_NGU = 36;
//	public static final int STATEMENT = 12;
//	public static final int STOCK_STATEMENT = 14;
//	public static final int CASH_STATEMENT = 13;
//	public static final int SO_DU = 10;
//	public static final int ORDERLIST = 6;
//	public static final int GTC_ORDER_LIST = 8;
//	public static final int NORMAL_ORDER_LIST = 7;
//	public static final int ACCOUNT_BALANCE = 9;
//	public static final int THONG_DIEP_CTCK = 4;
//	public static final int THONG_TIN_NGAN_HANG = 37;
//	public static final int MARKET = 2;
//	public static final int MARKET_INFO = 3;
//	public static final int ADVANCE = 27;
//	public static final int ANALYSIS_NEWS = 41;
//	public static final int LIST_BANK_ACCTNO = 42;
//	public static final int DAT_LENH_GTC = 43;
//	public static final int ODD_ORDER_REGISTER = 44;
//	public static final int CONFIRM_ORDER = 45;
//	public static final int AUTHO_INFO = 46;
//	public static final int SEARCH_STOCK = 47;
//
//	String[] menuFunctionTitles;
//
//	ExpandableListAdapter adapter_expendable;
//	List<MenuFunctionItem> list_DataHeader;
//	HashMap<Integer, List<MenuFunctionItem>> list_DataChild;
//	ExpandableListView exlv_MenuFunction;
//
//	public static MenuFunction newInstance(MainActivity mActivity) {
//		MenuFunction self = new MenuFunction();
//		self.mainActivity = mActivity;
//		return self;
//	}
//
//	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
//			Bundle bundle) {
//		View view = inflater.inflate(R.layout.menu_function, viewGroup, false);
//		exlv_MenuFunction = ((ExpandableListView) view
//				.findViewById(R.id.listview_menufunction));
//		exlv_MenuFunction.setChoiceMode(1);
//
//		initialise();
//
//		return view;
//	}
//
//	private void initialise() {
//
//		if (menuFunctionTitles == null) {
//			menuFunctionTitles = getResources().getStringArray(
//					R.array.menu_function_title);
//		}
//		initialiseData();
//		initialiseListener();
//	}
//
//	private void initialiseData() {
//		// TODO Auto-generated method stub
//
//		if (StaticObjectManager.loginInfo.IsBroker.matches("true")) {
//			if (list_DataHeader == null) {
//				list_DataHeader = new ArrayList<MenuFunctionItem>();
//				/*
//				 * khởi tạo list_DataHeader
//				 */
//				// dat lenh
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[DAT_LENH],
//						R.drawable.image_datlenh_32x32, DAT_LENH, mainActivity,
//						PlaceOrder.class));
//				// dat lenh gtc
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[DAT_LENH_GTC],
//						R.drawable.image_datlenh_32x32, DAT_LENH_GTC,
//						mainActivity, GTCPlaceOrder.class));
//				// bang gia
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[BANG_GIA],
//						R.drawable.image_banggia_32x32, BANG_GIA, mainActivity,
//						WatchList.class));
//				// xac nhan lenh
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[CONFIRM_ORDER],
//						R.drawable.image_danhmuc_38x38, CONFIRM_ORDER,
//						mainActivity, ConfirmOrder.class));
//				// thi truong
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[MARKET], R.drawable.image_header,
//						MARKET, mainActivity, null));
//				// so lenh
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[ORDERLIST], R.drawable.image_header,
//						ORDERLIST, mainActivity, null));
//				// tai san
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[ACCOUNT_BALANCE],
//						R.drawable.image_header, ACCOUNT_BALANCE, mainActivity,
//						null));
//				// sao ke
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[STATEMENT], R.drawable.image_header,
//						STATEMENT, mainActivity, null));
//				// chuyen tien
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[CASH_TRANSFER],
//						R.drawable.image_header, CASH_TRANSFER, mainActivity,
//						null));
//				// giao dich khac
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[OTHER_TRADE],
//						R.drawable.image_header, OTHER_TRADE, mainActivity,
//						null));
//				// bo loc va canh bao
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[BO_LOC_VA_CANH_BAO],
//						R.drawable.image_header, BO_LOC_VA_CANH_BAO,
//						mainActivity, null));
//				// he thong va tai khoan
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[HE_THONG_VA_TAI_KHOAN],
//						R.drawable.image_header, HE_THONG_VA_TAI_KHOAN,
//						mainActivity, null));
//				// bao cao moi gioi
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[BAO_CAO_MOI_GIOI],
//						R.drawable.image_baocao_48x48, BAO_CAO_MOI_GIOI,
//						mainActivity, Report.class));
//				// Thong tin lien he
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[THONG_TIN_NGAN_HANG],
//						R.drawable.ic_msmobile24, THONG_TIN_NGAN_HANG,
//						mainActivity, AboutUs.class));
//				// dang xuat
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[DANG_XUAT],
//						R.drawable.ic_logout, DANG_XUAT,
//						mainActivity, null));
//			}
//			if (list_DataChild == null) {
//				list_DataChild = new HashMap<Integer, List<MenuFunctionItem>>();
//				/*
//				 * khởi tạo list_DataChild
//				 */
//				// group thông tin thị trường - tin tổng hợp
//				List<MenuFunctionItem> list_MarketInfo = new ArrayList<MenuFunctionItem>();
//				list_MarketInfo.add(new MenuFunctionItem(1,
//						menuFunctionTitles[MARKET_INFO],
//						R.drawable.image_thitruong_48x48, MARKET_INFO,
//						mainActivity, MarketInfo.class));
//				// group thông tin thị trường - thông điệp ctck
//				list_MarketInfo.add(new MenuFunctionItem(1,
//						menuFunctionTitles[THONG_DIEP_CTCK],
//						R.drawable.image_news72x72, THONG_DIEP_CTCK,
//						mainActivity, MSBSMessage.class));
//
//				// group thông tin thị trường - tin từ ctck
//				list_MarketInfo.add(new MenuFunctionItem(1,
//						menuFunctionTitles[ANALYSIS_NEWS],
//						R.drawable.image_news72x72, ANALYSIS_NEWS,
//						mainActivity, AnalysisNews.class));
//				// group thông tin thị trường - lịch sự kiện
//				list_MarketInfo.add(new MenuFunctionItem(1,
//						menuFunctionTitles[LICH_SU_KIEN],
//						R.drawable.image_news72x72, LICH_SU_KIEN, mainActivity,
//						CoporateActions.class));
//				list_DataChild.put(MARKET, list_MarketInfo);
//
//				// group sổ lệnh - sổ lệnh thường
//				List<MenuFunctionItem> list_OrderList = new ArrayList<MenuFunctionItem>();
//				list_OrderList.add(new MenuFunctionItem(1,
//						menuFunctionTitles[NORMAL_ORDER_LIST],
//						R.drawable.image_solenh_32x32, NORMAL_ORDER_LIST,
//						mainActivity, NormalOrderList.class));
//				// group sổ lệnh - sổ lệnh điều kiện
//				list_OrderList.add(new MenuFunctionItem(1,
//						menuFunctionTitles[GTC_ORDER_LIST],
//						R.drawable.image_solenh_32x32, GTC_ORDER_LIST,
//						mainActivity, GTCOrderList.class));
//				list_DataChild.put(ORDERLIST, list_OrderList);
//
//				// group tài sản - số dư
//				List<MenuFunctionItem> list_Balance = new ArrayList<MenuFunctionItem>();
//				list_Balance.add(new MenuFunctionItem(1,
//						menuFunctionTitles[SO_DU], R.drawable.image_sodu_32x32,
//						SO_DU, mainActivity, AccountBalance.class));
//				// group tài sản - danh mục chứng khoán
//				list_Balance.add(new MenuFunctionItem(1,
//						menuFunctionTitles[PORFOLIO],
//						R.drawable.image_danhmuc_38x38, PORFOLIO, mainActivity,
//						Porfolio.class));
//				list_DataChild.put(ACCOUNT_BALANCE, list_Balance);
//
//				// group sao kê - sao kê tiền
//				List<MenuFunctionItem> list_Statement = new ArrayList<MenuFunctionItem>();
//				list_Statement.add(new MenuFunctionItem(1,
//						menuFunctionTitles[CASH_STATEMENT],
//						R.drawable.image_saoketien_48x48_grey, CASH_STATEMENT,
//						mainActivity, CashStatement.class));
//				// group sao kê - sao kê chứng khoán
//				list_Statement.add(new MenuFunctionItem(1,
//						menuFunctionTitles[STOCK_STATEMENT],
//						R.drawable.image_saokeck_48x48_grey, STOCK_STATEMENT,
//						mainActivity, StockStatement.class));
//				// group sao kê - lịch sử khớp lệnh
//				list_Statement.add(new MenuFunctionItem(1,
//						menuFunctionTitles[ORDER_HISTORY],
//						R.drawable.image_lskhoplenh_48x48_grey, ORDER_HISTORY,
//						mainActivity, OrderHistory.class));
//				// group sao kê - lịch sử chuyển tiền
//				list_Statement.add(new MenuFunctionItem(1,
//						menuFunctionTitles[CASH_TRANSFER_HISTORY],
//						R.drawable.image_lschuyentien_48x48_grey,
//						CASH_TRANSFER_HISTORY, mainActivity,
//						CashTransferHistory.class));
//				// group sao kê - lịch sử đăng ký quyền mua
//				list_Statement.add(new MenuFunctionItem(1,
//						menuFunctionTitles[LICH_SU_DANG_KY_QUYEN_MUA],
//						R.drawable.image_lsdkqm_48x48_grey,
//						LICH_SU_DANG_KY_QUYEN_MUA, mainActivity,
//						RightOffRegisterHistory.class));
//				// group sao kê - lịch sử ứng trước
//				list_Statement.add(new MenuFunctionItem(1,
//						menuFunctionTitles[ADVANCE_HISTORY],
//						R.drawable.image_lsungtruoc_48x48_grey,
//						ADVANCE_HISTORY, mainActivity, AdvanceHistory.class));
//				list_DataChild.put(STATEMENT, list_Statement);
//
//				// group chuyển tiền - danh sách người thụ hưởng
//				List<MenuFunctionItem> list_CashTransfer = new ArrayList<MenuFunctionItem>();
//				list_CashTransfer.add(new MenuFunctionItem(1,
//						menuFunctionTitles[LIST_BANK_ACCTNO],
//						R.drawable.image_lsdkqm_48x48_grey, LIST_BANK_ACCTNO,
//						mainActivity, CashTransferList.class));
//				// group chuyển tiền - chuyển tiền nội bộ
//				list_CashTransfer.add(new MenuFunctionItem(1,
//						menuFunctionTitles[INTERNAL_CASH_TRANSFER],
//						R.drawable.image_chuyentien_48x48_grey,
//						INTERNAL_CASH_TRANSFER, mainActivity,
//						InternalCashTransfer.class));
//				// group chuyển tiền - chuyển tiền sang ngân hàng
//				list_CashTransfer.add(new MenuFunctionItem(1,
//						menuFunctionTitles[STOCK_COMPANY_CASH_TRANSFER],
//						R.drawable.image_chuyentien_48x48_grey,
//						STOCK_COMPANY_CASH_TRANSFER, mainActivity,
//						SCCashTransfer.class));
//				// group chuyển tiền - chuyển tiền ra ngoài
//				list_CashTransfer.add(new MenuFunctionItem(1,
//						menuFunctionTitles[BANK_CASH_TRANSFER],
//						R.drawable.image_chuyentien_48x48_grey,
//						BANK_CASH_TRANSFER, mainActivity,
//						BankCashTransfer.class));
//				// group chuyển tiền - đăng ký chuyển tiền nội bộ
//				list_CashTransfer.add(new MenuFunctionItem(1,
//						menuFunctionTitles[INTERNAL_CASH_TRANSFER_REGISTER],
//						R.drawable.image_dkct_48x48_grey,
//						INTERNAL_CASH_TRANSFER_REGISTER, mainActivity,
//						InternalCashTransferRegister.class));
//				// group chuyển tiền - đăng ký chuyển tiền sang ngân hàng
//				list_CashTransfer.add(new MenuFunctionItem(1,
//						menuFunctionTitles[SC_CASH_TRANSFER_REGISTER],
//						R.drawable.image_dkct_48x48_grey,
//						SC_CASH_TRANSFER_REGISTER, mainActivity,
//						SCCashTransferRegister.class));
//				// group chuyển tiền - đăng ký chuyển tiền ra ngoài
//				list_CashTransfer.add(new MenuFunctionItem(1,
//						menuFunctionTitles[BANK_CASH_TRANSFER_REGISTER],
//						R.drawable.image_dkct_48x48_grey,
//						BANK_CASH_TRANSFER_REGISTER, mainActivity,
//						BankCashTransferRegister.class));
//				list_DataChild.put(CASH_TRANSFER, list_CashTransfer);
//
//				// group giao dịch khác - ứng trước
//				List<MenuFunctionItem> list_AnotherPayments = new ArrayList<MenuFunctionItem>();
//				list_AnotherPayments.add(new MenuFunctionItem(1,
//						menuFunctionTitles[ADVANCE],
//						R.drawable.image_ungtruoc_48x48_grey, ADVANCE,
//						mainActivity, Advance.class));
//				// group giao dịch khác - chuyển khoản chứng khoán
//				list_AnotherPayments.add(new MenuFunctionItem(1,
//						menuFunctionTitles[STOCK_TRANSFER],
//						R.drawable.image_chuyenkhoanck_48x48_grey,
//						STOCK_TRANSFER, mainActivity, StockTransfer.class));
//				// group giao dịch khác - đăng ký quyền mua
//				list_AnotherPayments.add(new MenuFunctionItem(1,
//						menuFunctionTitles[RIGHT_OFF_REGISTER],
//						R.drawable.image_dkqm_48x48_grey, RIGHT_OFF_REGISTER,
//						mainActivity, RightOffRegister.class));
//				// group giao dịch khác - đăng ký bán lô lẻ
//				list_AnotherPayments.add(new MenuFunctionItem(1,
//						menuFunctionTitles[ODD_ORDER_REGISTER],
//						R.drawable.image_dkqm_48x48_grey, ODD_ORDER_REGISTER,
//						mainActivity, OddOrderRegister.class));
//				// group giao dịch khác - tra cứu chứng khoán
//				list_AnotherPayments.add(new MenuFunctionItem(1,
//						menuFunctionTitles[SEARCH_STOCK],
//						R.drawable.image_lsungtruoc_48x48_grey, SEARCH_STOCK,
//						mainActivity, SearchStock.class));
//				list_DataChild.put(OTHER_TRADE, list_AnotherPayments);
//
//				// group bộ lọc và cảnh báo - bộ lọc cổ phiếu
//				List<MenuFunctionItem> list_Filter = new ArrayList<MenuFunctionItem>();
//				list_Filter
//						.add(new MenuFunctionItem(1,
//								menuFunctionTitles[BO_LOC_CO_PHIEU],
//								R.drawable.image_boloccophieu_48x48_grey,
//								BO_LOC_CO_PHIEU, mainActivity,
//								BoLocCP_ViewPager.class));
//				// group bộ lọc và cảnh báo - bộ lọc cảnh báo
//				list_Filter.add(new MenuFunctionItem(1,
//						menuFunctionTitles[BO_LOC_CANH_BAO],
//						R.drawable.image_boloccanhbao_48x48_grey,
//						BO_LOC_CANH_BAO, mainActivity, Alert_ViewPager.class));
//				list_DataChild.put(BO_LOC_VA_CANH_BAO, list_Filter);
//
//				// group hệ thống và tài khoản - đổi mật khẩu đăng nhập
//				List<MenuFunctionItem> list_System = new ArrayList<MenuFunctionItem>();
//				list_System.add(new MenuFunctionItem(1,
//						menuFunctionTitles[ACCOUNT_CONFIG],
//						R.drawable.image_change_password_48x48, ACCOUNT_CONFIG,
//						mainActivity, AccountConfig.class));
//				list_System.add(new MenuFunctionItem(1,
//						menuFunctionTitles[AUTHO_INFO],
//						R.drawable.image_saokeck_48x48_grey, AUTHO_INFO,
//						mainActivity, AuthorizationInfo.class));
//				list_DataChild.put(HE_THONG_VA_TAI_KHOAN, list_System);
//			}
//		} else {
//
//			if (list_DataHeader == null) {
//				list_DataHeader = new ArrayList<MenuFunctionItem>();
//				/*
//				 * khởi tạo list_DataHeader
//				 */
//				// dat lenh
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[DAT_LENH],
//						R.drawable.image_datlenh_32x32, DAT_LENH, mainActivity,
//						PlaceOrder.class));
//				// dat lenh gtc
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[DAT_LENH_GTC],
//						R.drawable.image_datlenh_32x32, DAT_LENH_GTC,
//						mainActivity, GTCPlaceOrder.class));
//				// bang gia
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[BANG_GIA],
//						R.drawable.image_banggia_32x32, BANG_GIA, mainActivity,
//						WatchList.class));
//				// xac nhan lenh
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[CONFIRM_ORDER],
//						R.drawable.image_danhmuc_38x38, CONFIRM_ORDER,
//						mainActivity, ConfirmOrder.class));
//				// thi truong
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[MARKET], R.drawable.image_header,
//						MARKET, mainActivity, null));
//				// so lenh
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[ORDERLIST], R.drawable.image_header,
//						ORDERLIST, mainActivity, null));
//				// tai san
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[ACCOUNT_BALANCE],
//						R.drawable.image_header, ACCOUNT_BALANCE, mainActivity,
//						null));
//				// sao ke
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[STATEMENT], R.drawable.image_header,
//						STATEMENT, mainActivity, null));
//				// bo loc va canh bao
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[BO_LOC_VA_CANH_BAO],
//						R.drawable.image_header, BO_LOC_VA_CANH_BAO,
//						mainActivity, null));
//				// he thong va tai khoan
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[HE_THONG_VA_TAI_KHOAN],
//						R.drawable.image_header, HE_THONG_VA_TAI_KHOAN,
//						mainActivity, null));
//				// bao cao moi gioi
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[BAO_CAO_MOI_GIOI],
//						R.drawable.image_baocao_48x48, BAO_CAO_MOI_GIOI,
//						mainActivity, Report.class));
//				// Thong tin lien he
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[THONG_TIN_NGAN_HANG],
//						R.drawable.ic_msmobile24, THONG_TIN_NGAN_HANG,
//						mainActivity, AboutUs.class));
//				// dang xuat
//				list_DataHeader.add(new MenuFunctionItem(0,
//						menuFunctionTitles[DANG_XUAT],
//						R.drawable.ic_logout, DANG_XUAT,
//						mainActivity, null));
//			}
//			if (list_DataChild == null) {
//				list_DataChild = new HashMap<Integer, List<MenuFunctionItem>>();
//				/*
//				 * khởi tạo list_DataChild
//				 */
//				// group thông tin thị trường - tin tổng hợp
//				List<MenuFunctionItem> list_MarketInfo = new ArrayList<MenuFunctionItem>();
//				list_MarketInfo.add(new MenuFunctionItem(1,
//						menuFunctionTitles[MARKET_INFO],
//						R.drawable.image_thitruong_48x48, MARKET_INFO,
//						mainActivity, MarketInfo.class));
//				// group thông tin thị trường - thông điệp ctck
//				list_MarketInfo.add(new MenuFunctionItem(1,
//						menuFunctionTitles[THONG_DIEP_CTCK],
//						R.drawable.image_news72x72, THONG_DIEP_CTCK,
//						mainActivity, MSBSMessage.class));
//
//				// group thông tin thị trường - tin từ ctck
//				list_MarketInfo.add(new MenuFunctionItem(1,
//						menuFunctionTitles[ANALYSIS_NEWS],
//						R.drawable.image_news72x72, ANALYSIS_NEWS,
//						mainActivity, AnalysisNews.class));
//				// group thông tin thị trường - lịch sự kiện
//				list_MarketInfo.add(new MenuFunctionItem(1,
//						menuFunctionTitles[LICH_SU_KIEN],
//						R.drawable.image_news72x72, LICH_SU_KIEN, mainActivity,
//						CoporateActions.class));
//				list_DataChild.put(MARKET, list_MarketInfo);
//
//				// group sổ lệnh - sổ lệnh thường
//				List<MenuFunctionItem> list_OrderList = new ArrayList<MenuFunctionItem>();
//				list_OrderList.add(new MenuFunctionItem(1,
//						menuFunctionTitles[NORMAL_ORDER_LIST],
//						R.drawable.image_solenh_32x32, NORMAL_ORDER_LIST,
//						mainActivity, NormalOrderList.class));
//				// group sổ lệnh - sổ lệnh điều kiện
//				list_OrderList.add(new MenuFunctionItem(1,
//						menuFunctionTitles[GTC_ORDER_LIST],
//						R.drawable.image_solenh_32x32, GTC_ORDER_LIST,
//						mainActivity, GTCOrderList.class));
//				list_DataChild.put(ORDERLIST, list_OrderList);
//
//				// group tài sản - số dư
//				List<MenuFunctionItem> list_Balance = new ArrayList<MenuFunctionItem>();
//				list_Balance.add(new MenuFunctionItem(1,
//						menuFunctionTitles[SO_DU], R.drawable.image_sodu_32x32,
//						SO_DU, mainActivity, AccountBalance.class));
//				// group tài sản - danh mục chứng khoán
//				list_Balance.add(new MenuFunctionItem(1,
//						menuFunctionTitles[PORFOLIO],
//						R.drawable.image_danhmuc_38x38, PORFOLIO, mainActivity,
//						Porfolio.class));
//				list_DataChild.put(ACCOUNT_BALANCE, list_Balance);
//
//				// group sao kê - sao kê tiền
//				List<MenuFunctionItem> list_Statement = new ArrayList<MenuFunctionItem>();
//				list_Statement.add(new MenuFunctionItem(1,
//						menuFunctionTitles[CASH_STATEMENT],
//						R.drawable.image_saoketien_48x48_grey, CASH_STATEMENT,
//						mainActivity, CashStatement.class));
//				// group sao kê - sao kê chứng khoán
//				list_Statement.add(new MenuFunctionItem(1,
//						menuFunctionTitles[STOCK_STATEMENT],
//						R.drawable.image_saokeck_48x48_grey, STOCK_STATEMENT,
//						mainActivity, StockStatement.class));
//				// group sao kê - lịch sử khớp lệnh
//				list_Statement.add(new MenuFunctionItem(1,
//						menuFunctionTitles[ORDER_HISTORY],
//						R.drawable.image_lskhoplenh_48x48_grey, ORDER_HISTORY,
//						mainActivity, OrderHistory.class));
//				// group sao kê - lịch sử chuyển tiền
//				list_Statement.add(new MenuFunctionItem(1,
//						menuFunctionTitles[CASH_TRANSFER_HISTORY],
//						R.drawable.image_lschuyentien_48x48_grey,
//						CASH_TRANSFER_HISTORY, mainActivity,
//						CashTransferHistory.class));
//				// group sao kê - lịch sử đăng ký quyền mua
//				list_Statement.add(new MenuFunctionItem(1,
//						menuFunctionTitles[LICH_SU_DANG_KY_QUYEN_MUA],
//						R.drawable.image_lsdkqm_48x48_grey,
//						LICH_SU_DANG_KY_QUYEN_MUA, mainActivity,
//						RightOffRegisterHistory.class));
//				// group sao kê - lịch sử ứng trước
//				list_Statement.add(new MenuFunctionItem(1,
//						menuFunctionTitles[ADVANCE_HISTORY],
//						R.drawable.image_lsungtruoc_48x48_grey,
//						ADVANCE_HISTORY, mainActivity, AdvanceHistory.class));
//				list_DataChild.put(STATEMENT, list_Statement);
//
//				// group bộ lọc và cảnh báo - bộ lọc cổ phiếu
//				List<MenuFunctionItem> list_Filter = new ArrayList<MenuFunctionItem>();
//				list_Filter
//						.add(new MenuFunctionItem(1,
//								menuFunctionTitles[BO_LOC_CO_PHIEU],
//								R.drawable.image_boloccophieu_48x48_grey,
//								BO_LOC_CO_PHIEU, mainActivity,
//								BoLocCP_ViewPager.class));
//				// group bộ lọc và cảnh báo - bộ lọc cảnh báo
//				list_Filter.add(new MenuFunctionItem(1,
//						menuFunctionTitles[BO_LOC_CANH_BAO],
//						R.drawable.image_boloccanhbao_48x48_grey,
//						BO_LOC_CANH_BAO, mainActivity, Alert_ViewPager.class));
//				list_DataChild.put(BO_LOC_VA_CANH_BAO, list_Filter);
//
//				// group hệ thống và tài khoản - đổi mật khẩu đăng nhập
//				List<MenuFunctionItem> list_System = new ArrayList<MenuFunctionItem>();
//				list_System.add(new MenuFunctionItem(1,
//						menuFunctionTitles[ACCOUNT_CONFIG],
//						R.drawable.image_change_password_48x48, ACCOUNT_CONFIG,
//						mainActivity, AccountConfig.class));
//				list_System.add(new MenuFunctionItem(1,
//						menuFunctionTitles[AUTHO_INFO],
//						R.drawable.image_saokeck_48x48_grey, AUTHO_INFO,
//						mainActivity, AuthorizationInfo.class));
//				list_DataChild.put(HE_THONG_VA_TAI_KHOAN, list_System);
//			}
//		}
//
//		// if (adapter_expendable == null) {
//		// adapter_expendable = new ExpandableListAdapter(getActivity(),
//		// list_DataHeader, list_DataChild);
//		// }
//		exlv_MenuFunction.setAdapter(adapter_expendable);
//
//	}
//
//	private void initialiseListener() {
//
//		exlv_MenuFunction.setOnGroupClickListener(new OnGroupClickListener() {
//
//			@Override
//			public boolean onGroupClick(ExpandableListView parent, View v,
//					int groupPosition, long id) {
//				// TODO Auto-generated method stub
//				list_DataHeader.get(groupPosition).performAction();
//				return false;
//			}
//		});
//
//		exlv_MenuFunction.setOnChildClickListener(new OnChildClickListener() {
//
//			@Override
//			public boolean onChildClick(ExpandableListView parent, View v,
//					int groupPosition, int childPosition, long id) {
//				// TODO Auto-generated method stub
//
//				MenuFunctionItem groupItem = list_DataHeader.get(groupPosition);
//				MenuFunctionItem childItem = list_DataChild.get(
//						groupItem.getKeyId()).get(childPosition);
//				childItem.performAction();
//				return false;
//			}
//		});
//
//	}
//
//	@Override
//	public void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		exlv_MenuFunction.setSelection(0);
//	}
//
//	@Override
//	public void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		int count = adapter_expendable.getGroupCount();
//		for (int i = 0; i < count; i++) {
//			exlv_MenuFunction.collapseGroup(i);
//		}
//
//	}
//
//	@Override
//	protected void process(String key, ResultObj rObj) {
//		// TODO Auto-generated method stub
//
//	}

}
