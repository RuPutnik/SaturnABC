package ru.putnik.saturn.ciphers;

import ru.putnik.saturn.main.CreationAlerts;

import java.util.Random;

public class ExpandCipher extends Cipher{
    public ExpandCipher(int numberCipher){
        super(numberCipher);
        nameCipher="Шифр расширения словом";
        nameFileInfo="extensionCipher.txt";
    }
    @Override
    public String crypt(String text, String key, int direction) {
        CodeWordCipher cipher=new CodeWordCipher();
        StringBuilder resultText= new StringBuilder();
        StringBuilder clearText=new StringBuilder();
        StringBuilder temp1=new StringBuilder();//для цифер
        StringBuilder temp2=new StringBuilder();//для букв
        int g=1;
        int args[]=null;
        int lineArg=0;
        int d=0;
        for(int l=0;l<key.length();l++){
            if(key.charAt(l)>=((int)('0'))&&key.charAt(l)<=((int)('9'))){
                temp1.append(key.charAt(l));
            }else{
                temp2.append(key.charAt(l));
            }
        }
        key=temp2.toString();
        if(temp1.length()!=0){
            lineArg=temp1.length();
            args=new int[lineArg];
            for(int k=0;k<lineArg;k++){
                args[k]=Integer.parseInt(String.valueOf(temp1.toString().charAt(k)));
            }

            g=Integer.parseInt(String.valueOf(temp1.toString().charAt(lineArg-1)));
        }
        if(direction==1){
            String cryptWordText=cipher.crypt(text,key,direction);
            for(int a=0;a<cryptWordText.length();a++){
                resultText.append(cryptWordText.charAt(a));
                for(int k=0;k<lineArg-1;k++){
                    if(((int)cryptWordText.charAt(a))%args[k]==0){
                        resultText.append(cryptWordText.charAt(a/g+args[k]+d));
                        d++;
                        break;
                    }
                }
            }
        }else if(direction==-1){
            for(int a=0;a<text.length();a++){
                clearText.append(text.charAt(a));
                for(int k=0;k<lineArg-1;k++){
                    if((int)text.charAt(a)%args[k]==0){
                        a++;
                        break;
                    }
                }
            }
            resultText = new StringBuilder(cipher.crypt(clearText.toString(), key, direction));
        }
        return resultText.toString();
    }

    @Override
    public boolean checkKey(String key) {
        if(key==null||key.equals("")){
            CreationAlerts.showWarningAlert("Ошибка","Ошибка шифрования","Поля ключа пусто!",false);
            return false;
        }else
            return true;
    }

    @Override
    public String generateKey() {
        Random random=new Random();
        int lengthCodeWordFirstPart=2+random.nextInt(8);
        StringBuilder codeWord=new StringBuilder();

        for (int a=0;a<lengthCodeWordFirstPart;a++){
            codeWord.append((char)(48+random.nextInt(74)));
            if(codeWord.charAt(a)==':'){
                codeWord.deleteCharAt(a);
                a--;
            }
        }
        //Сформировали первую часть кодового слова(то что до двоеточия)

        //Теперь формируем вторую часть(цифры и двоеточие)

        codeWord.append(":");
        int lengthCodeWordSecondPart=1+random.nextInt(2);
        int digitsGenerated[]=new int[lengthCodeWordSecondPart];
        for (int b=0;b<lengthCodeWordSecondPart;b++){
            int digit=2 + random.nextInt(7);

            if(b>0) {
                if (checkDigit(digit, digitsGenerated)) {
                    digitsGenerated[b] = digit;
                    codeWord.append(digitsGenerated[b]);
                } else {
                    b--;
                }
            }else {
                digitsGenerated[b] = digit;
                codeWord.append(digitsGenerated[b]);
            }
        }
        return codeWord.toString();
    }
    //Метод проверяет, может ли данное число быть добавленным в массив чисел, вставляемых после двоеточия в кодовом слове
    //Правила такие: 1) цифры не могут повторяться, 2)если одна из цифер явлется множителем(делителем) другой,
    // то они могут распологаться только в порядке убывания, например 8 4 2, но не 2 4 8, иначе 4 и 8 никогда не будут задействованы
    private boolean checkDigit(int digit,int arrayDigits[]){
        boolean result=true;

        for (int digitOfArray:arrayDigits){
            if(digit==digitOfArray) return false;//проверка на повторения

            if(digitOfArray!=0) {//Поскольку не все элементы массива еще заполнены
                if (digitOfArray % digit == 0) return false;//проверка 2 условия
            }
        }
        return result;
    }
}
