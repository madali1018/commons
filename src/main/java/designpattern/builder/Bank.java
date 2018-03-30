package designpattern.builder;

/**
 * Created by madali on 2018/3/16.
 */
public class Bank {

    private Integer id;
    private String bankCode;
    private String bankName;

    public Bank() {
    }

    private Bank(Builder builder) {
        id = builder.id;
        bankCode = builder.bankCode;
        bankName = builder.bankName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Bank bank) {
        return new Builder().id(bank.id).bankCode(bank.bankCode).bankName(bank.bankName);
    }

    public static final class Builder {
        private Integer id;
        private String bankCode;
        private String bankName;

        private Builder() {
        }

        private Builder id(Integer value) {
            id = value;
            return this;
        }

        private Builder bankCode(String value) {
            bankCode = value;
            return this;
        }

        private Builder bankName(String value) {
            bankName = value;
            return this;
        }

        public Bank build() {
            return new Bank(this);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bank{");
        sb.append("id=").append(id);
        sb.append(", bankCode='").append(bankCode).append('\'');
        sb.append(", bankName='").append(bankName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static void main(String[] args) {
        Bank bank = Bank.newBuilder().id(1).bankCode("ICBC").bankName("工商银行").build();
        System.out.println(bank);
        bank = Bank.newBuilder(bank).bankName("中国工商银行").build();
        System.out.println(bank);

    }

}
