package com.company;

import categories.Week;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    String chat_id;
    static String chosen;
    static Week week = new Week();
    static List<KeyboardRow> keyboardRows = new ArrayList<>(); //array of rows


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("Получить гороскоп\uD83D\uDD2E")) {
                    try {
                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
                        keyboardRows.clear();
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if (update.getMessage().getText().equals("Весы♎️") //1
                        || update.getMessage().getText().equals("Рак♋️")
                        || update.getMessage().getText().equals("Овен♈️")
                        || update.getMessage().getText().equals("Телец♉️")
                        || update.getMessage().getText().equals("Близнецы♊️")
                        || update.getMessage().getText().equals("Лев♌️")
                        || update.getMessage().getText().equals("Дева♍️")
                        || update.getMessage().getText().equals("Скорпион♏️")
                        || update.getMessage().getText().equals("Стрелец♐️")
                        || update.getMessage().getText().equals("Козерог♑️")
                        || update.getMessage().getText().equals("Водолей♒️")
                        || update.getMessage().getText().equals("Рибы♓️")) {
                    try {
                        execute(znak(update.getMessage().getChatId(), update.getMessage().getText()));
                        execute(changeBoard(update.getMessage().getChatId()));
                        keyboardRows.clear();
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if (update.getMessage().getText().equals("1")||update.getMessage().getText().equals("Гороскоп на неделю")) {
                    try {
                        execute(weekInfo(update.getMessage().getChatId()));
                        keyboardRows.clear();
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if (update.getMessage().getText().equals("2")||update.getMessage().getText().equals("Гороскоп на месяц")) {
                    try {
                        execute(monthInfo(update.getMessage().getChatId()));
                        keyboardRows.clear();
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }else if (update.getMessage().getText().equals("На главное меню")||update.getMessage().getText().equals("/start")) {
                    try {
                        execute(menu(update.getMessage().getChatId()));
                        keyboardRows.clear();
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else if (update.hasCallbackQuery()) {
            try {
                SendMessage m = new SendMessage();
                m.setText(update.getCallbackQuery().getData());
                m.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
                execute(m);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }



    private static SendMessage changeBoard(Long chatId) {
        keyboardRows.clear();
        SendMessage text = new SendMessage();
        text.setChatId(String.valueOf(chatId));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        KeyboardButton b1 = new KeyboardButton();
        KeyboardButton b2= new KeyboardButton();
//        KeyboardButton b3= new KeyboardButton();
        KeyboardButton b0= new KeyboardButton();
        text.setText("❇️Больше гороскопов:");
        b1.setText("Гороскоп на неделю");
        b2.setText("Гороскоп на месяц");
        b0.setText("На главное меню");

        row1.add(b1);
        row1.add(b2);
        row2.add(b0);

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        text.setReplyMarkup(replyKeyboardMarkup);
        return text;
    }

    private static SendMessage menu(Long chatId) {
        keyboardRows.clear();
        SendMessage text = new SendMessage();
        text.setChatId(String.valueOf(chatId));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton b1 = new KeyboardButton();
        text.setText("Добро пожаловать в бот для получения гороскопов. Для получения гороскопа выберете соответвующий пункт меню:");
        b1.setText("Получить гороскоп\uD83D\uDD2E");
        row1.add(b1);
        keyboardRows.add(row1);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        text.setReplyMarkup(replyKeyboardMarkup);
        return text;
    }

    private static SendMessage weekInfo(Long chatId) {
        keyboardRows.clear();
        SendMessage text = new SendMessage();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton b = new KeyboardButton();
        b.setText("Гороскоп на месяц");
        row.add(b);
        KeyboardRow row2 = new KeyboardRow();
        KeyboardButton b2 = new KeyboardButton();
        b2.setText("На главное меню");
        row2.add(b2);
        keyboardRows.add(row);
        keyboardRows.add(row2);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        text.setReplyMarkup(replyKeyboardMarkup);
        text.setChatId(chatId.toString());
        if (chosen == null) {
            text.setText("⚠️Выберете знак зодиака для продолжения");
            return text;
        } else {
            String url = "https://www.elle.ru/astro/" + chosen + "/week/";

            Document document = null;
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements ar = document.getElementsByClass("articleParagraph articleParagraph_dropCap");
            //System.out.println(ar.text());


            text.setText(chosen + "\n" + ar.text());
        }

        return text;


    }

    private static SendMessage monthInfo(Long chatId) {
        keyboardRows.clear();
        SendMessage text = new SendMessage();
        text.setChatId(chatId.toString());

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton b01 = new KeyboardButton();
//        KeyboardButton b02 = new KeyboardButton();
        b01.setText("Гороскоп на неделю");

        row1.add(b01);
//        row1.add(b02);
        KeyboardRow row2 = new KeyboardRow();
        KeyboardButton b2 = new KeyboardButton();
        b2.setText("На главное меню");
        row2.add(b2);
        keyboardRows.add(row1);
        keyboardRows.add(row2);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        text.setReplyMarkup(replyKeyboardMarkup);

        if (chosen == null) {
            text.setText("Выберете знак зодиака для продолжения");
            return text;
        } else {
            String url = "https://www.elle.ru/astro/" + chosen + "/month/";
            Document document = null;
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements ar = document.getElementsByClass("articleParagraph articleParagraph_dropCap");
            //System.out.println(ar.text());


            text.setText(chosen + "\n" + ar.text());
        }
        return text;

    }


    public static SendMessage sendInlineKeyBoardMessage(long chatId) {
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
//        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
//
//        inlineKeyboardButton1.setText("Весы");
//        inlineKeyboardButton1.setCallbackData("ll");
//
//        inlineKeyboardButton2.setText("Рак");
//        inlineKeyboardButton2.setCallbackData("Вы выбрали: Рак ");
//
//        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
//        keyboardButtonsRow1.add(inlineKeyboardButton1);
//        keyboardButtonsRow2.add(inlineKeyboardButton2);
//        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//        rowList.add(keyboardButtonsRow1);
//        rowList.add(keyboardButtonsRow2);
//        inlineKeyboardMarkup.setKeyboard(rowList);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        KeyboardRow row1 = new KeyboardRow(); //first row
        KeyboardRow row2 = new KeyboardRow();    //second row
        KeyboardRow row3 = new KeyboardRow();    //second row
        //second row

        //First row buttons
        setNames(row1, row2, row3);
        //Adding a keyboard
        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        SendMessage s = new SendMessage();
        s.setChatId(String.valueOf(chatId));
        s.setText("Доступные знаки зодиака:");
        s.setReplyMarkup(replyKeyboardMarkup);

        return s;
    }

    private static void setNames(KeyboardRow row1, KeyboardRow row2, KeyboardRow row3) {
        KeyboardButton b1 = new KeyboardButton();
        b1.setText("Весы♎️");
        row1.add(b1);

        KeyboardButton b2 = new KeyboardButton();
        b2.setText("Рак♋️");
        row1.add(b2);

        KeyboardButton b3 = new KeyboardButton();
        b3.setText("Овен♈️");
        row1.add(b3);

        KeyboardButton b4 = new KeyboardButton();
        b4.setText("Телец♉️");
        row1.add(b4);

        KeyboardButton b11 = new KeyboardButton();
        b11.setText("Близнецы♊️️");
        row2.add(b11);

        KeyboardButton b22 = new KeyboardButton();
        b22.setText("Лев♌️");
        row2.add(b22);

        KeyboardButton b33 = new KeyboardButton();
        b33.setText("Скорпион♏️");
        row2.add(b33);

        KeyboardButton b44 = new KeyboardButton();
        b44.setText("Стрелец♐️");
        row2.add(b44);

        //row3
        KeyboardButton b111 = new KeyboardButton();
        b111.setText("Близнецы♊️️");
        row3.add(b111);

        KeyboardButton b222 = new KeyboardButton();
        b222.setText("Козерог♑️️");
        row3.add(b222);

        KeyboardButton b333 = new KeyboardButton();
        b333.setText("Водолей♒️");
        row3.add(b333);

        KeyboardButton b444 = new KeyboardButton();
        b444.setText("Рибы♓️");
        row3.add(b444);
    }

    private static SendMessage znak(Long chatId, String z) {

        switch (z) {
            case "Весы♎️":
                z = "libra";
                break;
            case "Рак♋️":
                z = "cancer";
                break;
            case "Овен♈️":
                z = "aries";
                break;
            case "Телец♉️":
                z = "taurus";
                break;
            case "Близнецы♊️":
                z = "gemini";
                break;
            case "Лев♌️":
                z = "leo";
                break;
            case "Дева♍️":
                z = "virgo";
                break;
            case "Скорпион♏️":
                z = "scorpio";
                break;
            case "Стрелец♐️":
                z = "sagittarius";
                break;
            case "Козерог♑️":
                z = "capricorn";
                break;
            case "Водолей♒️":
                z = "aquarius";
                break;
            case "Рибы♓️":
                z = "pisces";
                break;
        }

        String url = "https://www.elle.ru/astro/" + z + "/day/";
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements ar = document.getElementsByClass("articleParagraph articleParagraph_dropCap");
        //System.out.println(ar.text());
        SendMessage text = new SendMessage();
        text.setChatId(chatId.toString());
        text.setText(z + "\n" + ar.text());
        chosen = z;

//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//
//        InlineKeyboardButton but0 = new InlineKeyboardButton();
//        InlineKeyboardButton but1 = new InlineKeyboardButton();
//        InlineKeyboardButton but2 = new InlineKeyboardButton();
//        InlineKeyboardButton but4 = new InlineKeyboardButton();

//        but1.setText("Больше информации\uD83D\uDDD3");
//        //but1.setText("На неделю\uD83D\uDDD3");
//
//        //System.out.println(week.printText(z));
//       // but1.setUrl("https://www.elle.ru/astro/" + z + "/week/");
////        but1.setCallbackData("Гороскоп на неделю: /week_" + z);
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(String.valueOf(chatId));
//        sendMessage.setText(week.printText(z));
//
//
//        but2.setText("На год\uD83D\uDDD2");
//        but2.setUrl("https://www.elle.ru/astro/libra/" + z + "/year/");
//
//        but4.setText("Характеристика знака\uD83D\uDD0D");
//
//        //but4.setCallbackData(String.valueOf(week.printText(z)));
//        but4.setUrl("https://www.elle.ru/astro/" + z);
//
//        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
//        keyboardButtonsRow1.add(but1);
//        keyboardButtonsRow2.add(but2);
//        keyboardButtonsRow4.add(but4);
//        but0.setText("Больше гороскопов");
//
//        but0.setCallbackData("1.На неделю.\n2.На месяц");
//        keyboardRows.clear();
//
//        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//        keyboardButtonsRow1.add(but0);
//        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
////        rowList.add(keyboardButtonsRow1);
////        rowList.add(keyboardButtonsRow2);
////        rowList.add(keyboardButtonsRow3);
////        rowList.add(keyboardButtonsRow4);
//        rowList.add(keyboardButtonsRow1);
//        inlineKeyboardMarkup.setKeyboard(rowList);
//
//        text.setReplyMarkup(inlineKeyboardMarkup);
        keyboardRows.clear();
        return text;
    }


    @Override
    public String getBotUsername() {
        return "Horoscope bot";
    }

    @Override
    public String getBotToken() {
        return "2049299720:AAHTRl2AR17Y5suD6i1ON_9ohzjiESF0bLQ";
    }
}
