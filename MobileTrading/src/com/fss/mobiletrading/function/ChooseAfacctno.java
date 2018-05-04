package com.fss.mobiletrading.function;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fss.mobiletrading.adapter.ChonTieuKhoan_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MSTradeAppConfig;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.MyActionBar.Action;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;

import java.util.ArrayList;
import java.util.List;

public class ChooseAfacctno extends AbstractFragment {
    public static final String CHANGE_ACCTNO = "SuccessService#1";
    public static final String FINDCUSTODYCD = "FindCustodyCdService#FINDCUSTODYCD";

    TextView lbl_fullname;
    TextView tv_fullname;
    TextView tv_afacctno;
    EditText edt_CustodyCd;
    ListView lv_ChonTK;
    ImageButton btn_logout;
    Button btn_logout2;
    List<AcctnoItem> listTieuKhoan;
    ChonTieuKhoan_Adapter adapterChonTK;
    RelativeLayout lay_header;

    public static int selectedPosition;
    int temp_selectedPosition;

    public static ChooseAfacctno newInstance(MainActivity mActivity) {

        ChooseAfacctno self = new ChooseAfacctno();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.ChangeAccount);
        return self;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {
        if (mainActivity == null) {
            mainActivity = (MainActivity) getActivity();
        }
        View view = inflater.inflate(R.layout.chooseafacctno, container, false);
        lay_header = (RelativeLayout) view
                .findViewById(R.id.chooseafacctno_header);
        lbl_fullname = (TextView) view.findViewById(R.id.lbl_chonTK_fullname);
        tv_fullname = (TextView) view.findViewById(R.id.text_chonTK_fullname);
        tv_afacctno = (TextView) view.findViewById(R.id.text_chonTK_afacctno);
        edt_CustodyCd = ((EditText) view.findViewById(R.id.edt_chonTK_SoLuuKy));
        lv_ChonTK = ((ListView) view.findViewById(R.id.listview_chontieukhoan));
        btn_logout = (ImageButton) view
                .findViewById(R.id.btn_chooseafacctno_logout);
        btn_logout2 = (Button) view
                .findViewById(R.id.btn_chooseafacctno_logout2);
        initialise(view);
        if (DeviceProperties.isTablet) {
            Common.setupUI(view.findViewById(R.id.chooseafacctno),
                    this.getDialog());
        } else {
            Common.setupUI(view.findViewById(R.id.chooseafacctno),
                    getActivity());
        }
        if (hideHeader) {
            if (lay_header != null) {
                lay_header.setVisibility(View.GONE);
            }
            if (lbl_fullname != null) {
                lbl_fullname.setVisibility(View.GONE);
            }
            tv_fullname.setVisibility(View.GONE);
        }
        initialiseListener();
        return view;
    }

    int y = 0;
    int x = 0;
    int height;

    /**
     * hàm này dùng để set vị trí hiển thị dialog,
     *
     * @param x
     * @param y
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean hideHeader = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog d = super.onCreateDialog(savedInstanceState);
        Window w = d.getWindow();
        w.setBackgroundDrawableResource(R.drawable.bg_dialogfragment_bottom);
        WindowManager.LayoutParams l = w.getAttributes();
        w.setDimAmount(0.5f);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        w.setGravity(Gravity.BOTTOM | Gravity.LEFT);
        if ((x == 0 && y == 0)) {
            l.y = getDimenResource(R.dimen.t_footer_height);
            l.x = 0;
            height = getDimenResource(R.dimen.t_dialogchooseafacctno_height);
            hideHeader = false;
        } else {
            l.y = mainActivity.getHeightScreen() - y;
            l.x = x;
            height = y - 50;
            setPosition(0, 0);
            hideHeader = true;
        }
        return d;
    }

    @Override
    public void onStart() {
        super.onStart();
        int width = getDimenResource(R.dimen.t_dialogchooseafacctno_width);
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(width, height);
        }
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        showInformationAccount();
        adapterChonTK.setCheck(selectedPosition);
    }

    @Override
    public void addActionToActionBar() {
        super.addActionToActionBar();
        setBackLogoAction();
    }

    @Override
    public void setBackLogoAction() {
        if (!DeviceProperties.isTablet) {
            mainActivity.setHomeLogoAction(new Action() {

                @Override
                public void performAction(View view) {
                    mainActivity.backNavigateFragment();
                }

                @Override
                public int getDrawable() {

                    return R.drawable.ic_back;
                }

                @Override
                public String getText() {
                    return null;
                }
            });
        }
    }

    private void initialise(View view) {
        if (listTieuKhoan == null) {
            listTieuKhoan = new ArrayList<AcctnoItem>();
        }
        if (!DeviceProperties.isTablet) {
            btn_logout.setVisibility(View.GONE);
        }
        if (StaticObjectManager.loginInfo.IsBroker) {
            view.findViewById(R.id.lbl_chonTK_afacctno)
                    .setVisibility(View.GONE);
            view.findViewById(R.id.text_chonTK_afacctno).setVisibility(
                    View.GONE);
            edt_CustodyCd.setEnabled(true);
            edt_CustodyCd.setText(StringConst.EMPTY);
        } else {
            edt_CustodyCd.setEnabled(false);
            edt_CustodyCd.setText(StaticObjectManager.loginInfo.Custodycd);
        }
        listTieuKhoan = StaticObjectManager.loginInfo.contractList;
        adapterChonTK = new ChonTieuKhoan_Adapter(getActivity(),
                android.R.layout.simple_list_item_1, listTieuKhoan);
        lv_ChonTK.setAdapter(adapterChonTK);
    }

    private void initialiseListener() {
        btn_logout2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mainActivity.logout();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mainActivity.logout();
            }
        });
        edt_CustodyCd.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    lv_ChonTK.setVisibility(ListView.GONE);
                    listTieuKhoan.clear();
                    adapterChonTK.notifyDataSetChanged();

                } else {
                    CallFindCustodyCd();
                    lv_ChonTK.setVisibility(ListView.VISIBLE);
                }
            }
        });

        edt_CustodyCd.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == 66) {
                    CallFindCustodyCd();
                    lv_ChonTK.setVisibility(ListView.VISIBLE);
                    Common.hideSoftKeyboard(getActivity());
                }
                return false;
            }
        });

        edt_CustodyCd.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    edt_CustodyCd.setText(MSTradeAppConfig.USERNAME_DEFAULT);
                }
            }
        });
        lv_ChonTK.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (!(listTieuKhoan.get(position)).ACCTNO
                        .equals(StaticObjectManager.acctnoItem.ACCTNO)) {
                    CallChooseAcctno(listTieuKhoan.get(position).ACCTNO);
                    temp_selectedPosition = position;
                }
            }
        });
    }

    private void processFirstItemChoose() {
        if (listTieuKhoan.size() > 0) {
            CallChooseAcctno(listTieuKhoan.get(selectedPosition).ACCTNO);
            temp_selectedPosition = selectedPosition;
        }
    }

    private void CallChooseAcctno(String afacctno) {

        if (afacctno != null) {
            List<String> list_key = new ArrayList<String>();
            List<String> list_value = new ArrayList<String>();
            {
                list_key.add("link");
                list_value
                        .add(getStringResource(R.string.controller_ChooseAcctno));
            }
            {
                list_key.add("Acctno");
                list_value.add(afacctno);
            }
            StaticObjectManager.connectServer.callHttpPostService(
                    CHANGE_ACCTNO, this, list_key, list_value);
        }
    }

    public static void CallChooseAcctno(String afacctno, INotifier notifier) {

        if (afacctno != null) {
            List<String> list_key = new ArrayList<String>();
            List<String> list_value = new ArrayList<String>();
            {
                list_key.add("link");
                list_value.add(MSTradeAppConfig.controller_ChooseAcctno);
            }
            {
                list_key.add("Acctno");
                list_value.add(afacctno);
            }
            StaticObjectManager.connectServer.callHttpPostService(
                    CHANGE_ACCTNO, notifier, list_key, list_value);
        }
    }

    public void CallSyncChooseAcctno(String link, String afacctno,
                                     INotifier iNotifier) {

        if (afacctno != null) {
            List<String> list_key = new ArrayList<String>();
            List<String> list_value = new ArrayList<String>();
            {
                list_key.add("link");
                list_value.add(link);
            }
            {
                list_key.add("Acctno");
                list_value.add(afacctno);
            }
            StaticObjectManager.connectServer.callHttpPostService(
                    CHANGE_ACCTNO, iNotifier, list_key, list_value);

        }
    }

    private void CallFindCustodyCd() {
        if (!StaticObjectManager.loginInfo.IsBroker) {
            return;
        }
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value
                    .add(getStringResource(R.string.controller_FindCustodyCd));
        }
        {
            list_key.add("CustodyCd");
            list_value.add(edt_CustodyCd.getText().toString());
        }
        mainActivity.loadingScreen(true);
        StaticObjectManager.connectServer.callHttpPostService(FINDCUSTODYCD,
                this, list_key, list_value);
        selectedPosition = 0;
    }

    public static void CallFindCustodyCd(String custodycd, INotifier notifier) {
        if (!StaticObjectManager.loginInfo.IsBroker) {
            return;
        }
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(MSTradeAppConfig.controller_FindCustodyCd);
        }
        {
            list_key.add("CustodyCd");
            list_value.add(custodycd);
        }
        StaticObjectManager.connectServer.callHttpPostService(FINDCUSTODYCD,
                notifier, list_key, list_value);
    }

    public void CallSyncFindCustodyCd(String link, String custodycd,
                                      INotifier notifier) {
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(link);
        }
        {
            list_key.add("CustodyCd");
            list_value.add(custodycd);
        }

        StaticObjectManager.connectServer.callHttpPostService(FINDCUSTODYCD,
                notifier, list_key, list_value);
        selectedPosition = 0;
    }

    public void notifyChangeAcctNo() {
        super.notifyChangeAcctNo();
    }    @Override
         protected void isReceivedResponse(String key) {
        super.isReceivedResponse(key);
        switch (key) {
            case FINDCUSTODYCD:
                mainActivity.loadingScreen(false);
                break;
            case CHANGE_ACCTNO:
                break;
            default:
                break;
        }

    }



    @Override
    protected void process(String key, ResultObj rObj) {

        switch (key) {
            case FINDCUSTODYCD:
                if (rObj.obj != null) {
                    listTieuKhoan.clear();
                    List<AcctnoItem> list = ((List<AcctnoItem>) rObj.obj);
                    changeCustodyCd(list);
                    processFirstItemChoose();
                }
                break;
            case CHANGE_ACCTNO:
                updateAfacctno(temp_selectedPosition);
                selectedPosition = temp_selectedPosition;
                break;

            default:
                break;
        }
    }

    @Override
    protected void processErrorOther(String key, ResultObj rObj) {
        super.processErrorOther(key, rObj);
        switch (key) {
            case FINDCUSTODYCD:
                listTieuKhoan.clear();
                StaticObjectManager.loginInfo.contractList.clear();
                adapterChonTK.notifyDataSetChanged();
                StaticObjectManager.acctnoItem = StaticObjectManager.defaultbrokeracctno;
                mainActivity.onChangeAfacctno(MSTradeAppConfig.USERNAME_DEFAULT);
                edt_CustodyCd.setText(StringConst.EMPTY);
                // gọi lên server thông báo k chọn tài khoản nào
                // CallChooseAcctno(afacctno);
                break;

            default:
                break;
        }
    }

    private void updateAfacctno(int position) {
        StaticObjectManager.acctnoItem = listTieuKhoan.get(position);
        mainActivity
                .onChangeAfacctno(StaticObjectManager.acctnoItem.toString());
        showInformationAccount();
        adapterChonTK.setCheck(position);
    }

    private void showInformationAccount() {
        if (StaticObjectManager.loginInfo.IsBroker) {
            // nếu là tài khoản môi giới
            tv_fullname.setText(StaticObjectManager.loginInfo.UserName);
            edt_CustodyCd.setText(StaticObjectManager.acctnoItem.CUSTODYCD);
        } else {
            // nếu là tài khoản khách hàng
            tv_fullname.setText(StaticObjectManager.acctnoItem.FULLNAME);
            tv_afacctno.setText(StaticObjectManager.acctnoItem.toString());
            edt_CustodyCd.setText(StaticObjectManager.acctnoItem.CUSTODYCD);
        }

    }

    public void changeCustodyCd(List<AcctnoItem> list) {

        if (list != null) {
            if (listTieuKhoan == null) {
                listTieuKhoan = new ArrayList<AcctnoItem>();
            } else {
                listTieuKhoan.clear();
            }
            StaticObjectManager.loginInfo.contractList = list;
            listTieuKhoan.addAll(StaticObjectManager.loginInfo.contractList);
            if (adapterChonTK == null) {
                adapterChonTK = new ChonTieuKhoan_Adapter(mainActivity,
                        android.R.layout.simple_list_item_1, listTieuKhoan);
            } else {
                adapterChonTK.notifyDataSetChanged();
            }
        } else {
            StaticObjectManager.loginInfo.contractList.clear();
            StaticObjectManager.acctnoItem = StaticObjectManager.defaultbrokeracctno;
        }
    }

    public void changeAfacctno(String afacctno) {
        if (afacctno != null
                && !afacctno.equals(StaticObjectManager.acctnoItem.ACCTNO)) {
            for (int i = 0; i < StaticObjectManager.loginInfo.contractList
                    .size(); i++) {
                if (afacctno.equals(StaticObjectManager.loginInfo.contractList
                        .get(i).ACCTNO)) {
                    selectedPosition = i;
                    StaticObjectManager.acctnoItem = StaticObjectManager.loginInfo.contractList
                            .get(i);
                    mainActivity.onChangeAfacctno(afacctno);
                    return;
                }
            }
        }
    }
}
