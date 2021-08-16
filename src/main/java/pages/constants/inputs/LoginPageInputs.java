package pages.constants.inputs;

import base.BaseInputs;

public interface LoginPageInputs extends BaseInputs {

    enum LoginOperation {
        boutiqueRugsUserPassword{
            @Override
            public String toString() {
                return "BR User Password";
            }
        },
        boutiqueRugsUserEmail{
            @Override
            public String toString() {
                return "BR User Email";
            }
        }
    };

}
