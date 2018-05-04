package com.fss.mobiletrading.function;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.fss.designcontrol.SymbolEdittext;
import com.fss.mobiletrading.allocateguarantee.AllocateGuaranteeService;
import com.fss.mobiletrading.allocateguarantee.AllocateInfo;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.Rule;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.jsonservice.FindCustodyCdService;
import com.fss.mobiletrading.object.AcctnoItemChild;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MSTradeAppConfig;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.design.LabelContentLayout;
import com.msbuat.mobiletrading.design.MyContextMenu.OnItemSelectedListener;
import com.msbuat.mobiletrading.design.MySpinner;
import com.msbuat.mobiletrading.design.NumberEditText;

import java.util.ArrayList;
import java.util.List;

public class CapBaoLanh extends AbstractFragment {
    public static final String FINDACUSTODYCD = "FindCustodyCdServiceChild#FINDACUSTODYCD";
    public static final String ALLOCATEGUARANTEE = "GetAllocateGuaranteeService#ALLOCATEGUARANTEE";
    public static final String ALLOCATEANDGRANT = "GetAllocateGuaranteeService#ALLOCATEANDGRANT";
    public static final String ALLOCATEGRANT = "SuccessService#ALLOCATEGRANT";
    private static final String STRINGALL = "ALL";

    MySpinner subaccount;
    LabelContentLayout SoLuuKy;
    LabelContentLayout BaoLanhLyThuyet;
    NumberEditText UseAmount;
    LabelContentLayout Rtt;
    LabelContentLayout SoTienPheDuyet;
    LabelContentLayout NguoiPheDuyet;
    SymbolEdittext edt_listckallowedbuy;
    FindCustodyCdService findafacc;
    AllocateInfo allocate;
    List<AcctnoItemChild> listTKNhan;
    List<AcctnoItemChild> listSpin;
    String Str_subacc;
    String Str_useamount;
    Button confirm;
    String[] listAllStock;

    public static CapBaoLanh newInstance(MainActivity mActivity) {

        CapBaoLanh self = new CapBaoLanh();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.CapBaoLanh);
        return self;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.capbaolanh, container, false);
        initView(view);
        initData();
        initListener();
        Common.setupUI(view, mainActivity);
        // Common.registrySeparatorNumber(UseAmount.getEditContent());
        Common.registrySeparatorNumber(UseAmount);
        return view;
    }

    public void initData() {
        edt_listckallowedbuy.setInputType(InputType.TYPE_NULL);
        edt_listckallowedbuy.setTextIsSelectable(true);
        edt_listckallowedbuy.setRawInputType(InputType.TYPE_CLASS_TEXT);
        listTKNhan = new ArrayList<AcctnoItemChild>();
        listSpin = new ArrayList<AcctnoItemChild>();
        UseAmount.setText("");
        SoLuuKy.setText(MSTradeAppConfig.USERNAME_DEFAULT);
        listAllStock = StaticObjectManager.getListAllStock();
    }

    private void initView(View view) {
        subaccount = (MySpinner) view.findViewById(R.id.Spin_SoTieuKhoan);
        SoLuuKy = (LabelContentLayout) view.findViewById(R.id.edt_soLuuKy);
        BaoLanhLyThuyet = (LabelContentLayout) view
                .findViewById(R.id.text_BaoLanhLyThuyet);
        UseAmount = (NumberEditText) view.findViewById(R.id.edt_SoTienSuDung);
        Rtt = (LabelContentLayout) view.findViewById(R.id.text_Rtt);
        SoTienPheDuyet = (LabelContentLayout) view
                .findViewById(R.id.text_SoTienPheDuyet);
        NguoiPheDuyet = (LabelContentLayout) view
                .findViewById(R.id.text_NgPheDuyet);
        edt_listckallowedbuy = (SymbolEdittext) view
                .findViewById(R.id.edt_DSCKAllowedBuy);
        edt_listckallowedbuy.setSymbolEdittextType(SymbolEdittext.SymbolEdittextType.Multiple);
        edt_listckallowedbuy.setEnabled(false);
        confirm = (Button) view.findViewById(R.id.btn_Cbl_ChapNhan);
    }

    public void setAllocateInfo(AllocateInfo allocate) {
        if (allocate != null) {
            BaoLanhLyThuyet.setText(allocate.T0CAL);
            Rtt.setText(allocate.MARGINRATE);
            SoTienPheDuyet.setText(allocate.T0AMTPENDING);
            NguoiPheDuyet.setText(allocate.TLIDNAME);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        clearData();
    }

    private void initListener() {
        confirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // subaccount.getContextMenu().getSelectedItem() != null
                if (SoLuuKy.getText().toString()
                        .equals(MSTradeAppConfig.USERNAME_DEFAULT) || SoLuuKy.getText().length() == 0) {
                    showDialogMessage("",
                            getStringResource(R.string.CBL_Dialog_ErrSoLuuky));
                } else if (UseAmount.getText().length() == 0) {
                    showDialogMessage("",
                            getStringResource(R.string.CBL_Dialog_ErrUseAmount));
                } else if (allocate == null) {
                    showDialogMessage("",
                            getStringResource(R.string.CBL_Dialog_ErrCheckAllowcate));
                } else {
                    callDoGrant();
                }
            }

        });
        SoLuuKy.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                confirm.setEnabled(!hasFocus);
                if (!hasFocus) {
                    // clearData();
                    if (SoLuuKy.getText().length() > 0) {
                        CallFindCustodyCd();
                    } else {
                        showDialogMessage(
                                "",
                                getStringResource(R.string.CBL_Dialog_ErrSoLuuky));
                        SoLuuKy.getEditContent().setFocusable(true);
                        clearData();
                    }
                } else {
                    SoLuuKy.getEditContent().setSelection(SoLuuKy.getText().length());
                }
            }
        });

        subaccount.getContextMenu().setOnItemSelectedListener(
                new OnItemSelectedListener() {

                    @Override
                    public void onItemSelect(Object obj, int position) {
//                        clearData();
                        UseAmount.setText("0");
                        callGetAllowGuarantee();
                        edt_listckallowedbuy.setText(StringConst.EMPTY);
                    }
                });
        UseAmount.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                confirm.setEnabled(!hasFocus);
                if (!hasFocus) {
                    callGetAllowGuarantee();
                }
            }
        });
        edt_listckallowedbuy.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edt_listckallowedbuy.setSelection(edt_listckallowedbuy.getText().length());
                    mainActivity.showKBoardHook(true, edt_listckallowedbuy, listAllStock);
                    mainActivity.showKBoardSymbol(true, edt_listckallowedbuy);
                } else {
                    mainActivity.showKBoardSymbol(false, null);
                    mainActivity.showKBoardHook(false, null, null);
                    checkSymBolRule(edt_listckallowedbuy.getText().toString());
                }
            }
        });
        edt_listckallowedbuy.addTextChangedListener(new TextWatcher() {
            String oldText;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                oldText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                int selectionEnd = edt_listckallowedbuy.getSelectionEnd();
                String selectedText = edt_listckallowedbuy.getText().toString().substring(0, selectionEnd);

                String text = s.toString();
                if (!text.equals(text.toUpperCase())) {
                    text = text.toUpperCase();
                    edt_listckallowedbuy.setText(text);
                    edt_listckallowedbuy.setSelection(selectedText.length());
                }
                if (!Rule.checkSymbol(text)) {
                    edt_listckallowedbuy.removeTextChangedListener(this);
                    s.clear();
                    s.append(oldText);
                    selectedText = oldText;
                    edt_listckallowedbuy.addTextChangedListener(this);
                }
                edt_listckallowedbuy.setSelection(selectedText.length());
            }
        });
    }

    private void checkSymBolRule(String symbolList) {
        if (!Rule.checkSymbol(symbolList)) {
            showDialogMessage(StringConst.EMPTY, getStringResource(R.string.cbl_formatedsymbolwrong), new SimpleAction() {
                @Override
                public void performAction(Object obj) {
                    edt_listckallowedbuy.setText(StringConst.EMPTY);
                }
            });
        }

    }

    protected void callGetAllowGuarantee() {
        // TODO Auto-generated method stub
        mainActivity.loadingScreen(true);
        clearWhenCallAllowcate();
        if (subaccount.getContextMenu().getSelectedItem() != null) {
            String afacctno = ((AcctnoItemChild) subaccount.getContextMenu()
                    .getSelectedItem()).ACCTNO;
            confirm.setEnabled(false);
            CallGetAllocateGuaranteeService(afacctno, ALLOCATEGUARANTEE);
        }
    }

    public void CallGetAllocateGuaranteeService(String pv_subacc, String key) {
        AllocateGuaranteeService.getAllocateT0(pv_subacc, UseAmount.getText()
                .toString(), this, key, StaticObjectManager.connectServer);

    }

    public void callDoGrant() {
        if (allocate != null) {
            if (allocate.WARN_MESSAGE.length() > 0) {
                showDialogMessage("", allocate.WARN_MESSAGE, new SimpleAction() {

                    @Override
                    public void performAction(Object obj) {
                        // TODO Auto-generated method stub
                        CallDoGrantEX();

                    }
                }, new SimpleAction() {

                    @Override
                    public void performAction(Object obj) {
                        // TODO Auto-generated method stub
                    }
                }, getStringResource(R.string.Yes), getStringResource(R.string.No));

            } else {
                CallDoGrantEX();
            }
        }
    }

    public void CallDoGrantEX() {
        String symbolamt = "";
        String text_listckallowedbuy = edt_listckallowedbuy
                .getText().toString();
        boolean isSymbolAllowBuy = allocate.getSymbolFlag();
        long t0AmtPending = allocate.getT0AMTPENDING();
        if (allocate != null) {
            if (!isSymbolAllowBuy) {
                callDoGrantT0(STRINGALL);
            } else {
                if (t0AmtPending == 0) {
                    if (text_listckallowedbuy.length() == 0) {
                        showDialogMessage("",
                                getResources().getString(R.string.CBL_requestinputsymbolequalsempty), new SimpleAction() {
                                    @Override
                                    public void performAction(Object obj) {
                                        edt_listckallowedbuy.setText(StringConst.EMPTY);
                                        edt_listckallowedbuy.requestFocus();
                                    }
                                });
                    } else {
                        symbolamt = text_listckallowedbuy;
                        if (text_listckallowedbuy.equals(STRINGALL)) {
                            final String finalSymbolamt = symbolamt;
                            showDialogMessage(StringConst.EMPTY, getStringResource(R.string.cbl_questionallsymbolallowed), new SimpleAction() {
                                @Override
                                public void performAction(Object obj) {
                                    callDoGrantT0(finalSymbolamt);
                                }
                            }, new SimpleAction() {
                                @Override
                                public void performAction(Object obj) {
                                    setLayoutWhenChoseNegativeAction();
                                }
                            }, getStringResource(R.string.cbl_OK), getStringResource(R.string.cbl_Not));
                        } else {
                            callDoGrantT0(symbolamt);
                        }
                    }
                } else {
                    if (text_listckallowedbuy.length() == 0) {
                        showDialogMessage("",
                                getResources().getString(R.string.CBL_requestinputsymbolequalsempty), new SimpleAction() {
                                    @Override
                                    public void performAction(Object obj) {
                                        edt_listckallowedbuy.setText(StringConst.EMPTY);
                                        edt_listckallowedbuy.requestFocus();
                                    }
                                });
                    } else if (text_listckallowedbuy.equals(STRINGALL)) {
                        showDialogMessage("", getResources().getString(R.string.CBL_requestinputsymbol), new SimpleAction() {
                            @Override
                            public void performAction(Object obj) {
                                edt_listckallowedbuy.setText(StringConst.EMPTY);
                                edt_listckallowedbuy.requestFocus();
                            }
                        });
                    } else {
                        symbolamt = text_listckallowedbuy;
                        callDoGrantT0(symbolamt);
                    }
                }
            }
        }
    }

    private void setLayoutWhenChoseNegativeAction() {
        edt_listckallowedbuy.setText(StringConst.EMPTY);
        edt_listckallowedbuy.setSelection(edt_listckallowedbuy.getText().length());
        edt_listckallowedbuy.requestFocus();
    }

    private void callDoGrantT0(String symbolamt) {
        mainActivity.loadingScreen(true);
        AllocateGuaranteeService.doGrantT0("", symbolamt, this,
                ALLOCATEGRANT, StaticObjectManager.connectServer);
    }

    @Override
    protected void process(String key, ResultObj rObj) {
        switch (key) {
            case FINDACUSTODYCD:
                listTKNhan.clear();
                listSpin = (List<AcctnoItemChild>) rObj.obj;
                for (int i = 0; i < listSpin.size(); i++) {
                    listTKNhan.add(listSpin.get(i));
                }
                subaccount.setChoises(listTKNhan);
                break;
            case ALLOCATEGUARANTEE:
                if (subaccount.getContextMenu().getSelectedItem() != null) {
                    allocate = (AllocateInfo) rObj.obj;
                    setAllocateInfo(allocate);
                }
                checkSymbolFlag(allocate);
                break;
            case ALLOCATEGRANT:
                showDialogMessage("", rObj.EM);
                clearData();
                break;
        }
    }

    private void checkSymbolFlag(AllocateInfo item) {
        // TODO Auto-generated method stub
        if (item != null) {
            boolean isSymbolAllowBuy = item.getSymbolFlag();
            if (!isSymbolAllowBuy) {
                edt_listckallowedbuy.setVisibility(View.VISIBLE);
                edt_listckallowedbuy.setEnabled(false);
                edt_listckallowedbuy.setText(STRINGALL);
                edt_listckallowedbuy.setTextColor(getColorResource(R.color.green_text));
            } else {
                edt_listckallowedbuy.setVisibility(View.VISIBLE);
                edt_listckallowedbuy.setTextColor(getColorResource(R.color.white_text));
                edt_listckallowedbuy.setEnabled(true);
                edt_listckallowedbuy.setText(STRINGALL);

            }
        }
    }

    @Override
    protected void processErrorOther(String key, ResultObj rObj) {
        super.processErrorOther(key, rObj);
        switch (key) {
            case ALLOCATEGRANT:
                // if (subaccount.getContextMenu().getSelectedItem() != null) {
                // String afacctno = ((AcctnoItemChild) subaccount
                // .getContextMenu().getSelectedItem()).ACCTNO;
                // CallGetAllocateGuaranteeService(afacctno, ALLOCATEGUARANTEE);
                // }
                break;
            case FINDACUSTODYCD:
                clearData();
                break;
        }
    }

    @Override
    protected void isReceivedResponse(String key) {
        // TODO Auto-generated method stub
        super.isReceivedResponse(key);
        switch (key) {
            case ALLOCATEGUARANTEE:
                confirm.setEnabled(true);
                break;
            case FINDACUSTODYCD:
                break;
        }

        mainActivity.loadingScreen(false);

    }

    private void CallFindCustodyCd() {
        // if (!StaticObjectManager.loginInfo.IsBroker) {
        // return;
        // }
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value
                    .add(getStringResource(R.string.controller_FindCustodyCd));
        }
        {
            list_key.add("CustodyCd");
            list_value.add(SoLuuKy.getText().toString());
        }
        StaticObjectManager.connectServer.callHttpPostService(FINDACUSTODYCD,
                this, list_key, list_value);
    }

    public void clearData() {
        if (subaccount.getContextMenu().getSelectedItem() != null)
            listTKNhan.clear();
        subaccount.setChoises(listTKNhan);
        SoLuuKy.setText(MSTradeAppConfig.USERNAME_DEFAULT);
        BaoLanhLyThuyet.setText(StringConst.EMPTY);
        UseAmount.setText("0");
        Rtt.setText(StringConst.EMPTY);
        SoTienPheDuyet.setText(StringConst.EMPTY);
        NguoiPheDuyet.setText(StringConst.EMPTY);
        edt_listckallowedbuy.setText(StringConst.EMPTY);
        allocate = null;
    }

    private void clearWhenCallAllowcate() {
        SoTienPheDuyet.setText(StringConst.EMPTY);
        NguoiPheDuyet.setText(StringConst.EMPTY);
        allocate = null;
    }
}