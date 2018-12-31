package main.ui.formui;

import com.jfoenix.controls.JFXDatePicker;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import main.businesslogicservice.bookblservice.BookblService;
import main.businesslogicservice.formblservice.FormblService;
import main.rmi.RemoteHelper;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class BusinessListUIController {
    @FXML
    private TextField saleIncome;
    @FXML
    private TextField overflowIncome;
    @FXML
    private TextField voucherAmount;
    @FXML
    private TextField discountAmount;
    @FXML
    private TextField totalIncome;
    @FXML
    private TextField purchaseExpense;
    @FXML
    private TextField cashExpense;
    @FXML
    private TextField underflowExpense;
    @FXML
    private TextField giftExpense;
    @FXML
    private TextField totalExpense;
    @FXML
    private TextField profit;

    @FXML
    private JFXDatePicker beginTimePicker;
    @FXML
    private JFXDatePicker endTimePicker;
    @FXML
    private Button query;

    private FormblService formblService;
    private BookblService bookblService;
    private final String pattern = "yyyy-MM-dd";
    private SimpleDateFormat format;
    private StringConverter<LocalDate> converter;
    private DecimalFormat df = new DecimalFormat("#.000");

    public void init() {
        formblService = RemoteHelper.getInstance().getFormblService();
        bookblService = RemoteHelper.getInstance().getBookblService();
        format = new SimpleDateFormat(pattern);
        converter = new StringConverter<LocalDate>() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                } else {
                    return null;
                }
            }
        };
        //设置格式转换器
        beginTimePicker.setConverter(converter);
        endTimePicker.setConverter(converter);

        //获得当前账簿起始时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate firstBook = null;
        try {
            firstBook = LocalDate.parse(format.format(bookblService.showBook().getTime().getTime()), formatter);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        LocalDate finalFirstBook = firstBook;

        //设置单元格工厂
        final Callback<DatePicker, DateCell> endCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(beginTimePicker.getValue() == null ? finalFirstBook : beginTimePicker.getValue().plusDays(1)) || item.isAfter(LocalDate.now().plusDays(1))) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };


        final Callback<DatePicker, DateCell> beginCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isAfter(endTimePicker.getValue() == null ? LocalDate.now() : endTimePicker.getValue().minusDays(1)) || item.isBefore(finalFirstBook)) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };

        beginTimePicker.setDayCellFactory(beginCellFactory);
        endTimePicker.setDayCellFactory(endCellFactory);
        endTimePicker.setValue(LocalDate.now().plusDays(1));


    }

    @FXML
    public void onQuery() {
        if (beginTimePicker.getValue() != null && endTimePicker.getValue() != null) {
            String begin = beginTimePicker.getValue().toString();
            String end = endTimePicker.getValue().toString();
            double[] list = null;
            Calendar beginCal = Calendar.getInstance();
            Calendar endCal = Calendar.getInstance();
            try {
                beginCal.setTime(format.parse(begin));
                endCal.setTime(format.parse(end));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                list = formblService.businessList(beginCal, endCal);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            assert list != null;
            saleIncome.setText(df.format(list[0]));
            overflowIncome.setText(df.format(list[3]));
            voucherAmount.setText(df.format(list[1]));
            discountAmount.setText(df.format(list[2]));
            totalIncome.setText(df.format(list[4]));
            purchaseExpense.setText(df.format(list[5]));
            cashExpense.setText(df.format(list[7]));
            underflowExpense.setText(df.format(list[6]));
            giftExpense.setText(df.format(list[8]));
            totalExpense.setText(df.format(list[9]));
            profit.setText(df.format(list[10]));
        }
    }
}
