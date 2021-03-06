package com.alttube.account.services;

import com.alttube.account.models.AccountExtrasModel;
import com.alttube.account.models.AccountModel;
import com.alttube.account.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final String path = Paths.get("").toAbsolutePath().toString() + "/account-images";
    private final AccountRepository accountRepository;
    private final SecurityService securityService;
    private final ExceptionService exceptionService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, SecurityService securityService, ExceptionService exceptionService) {
        this.accountRepository = accountRepository;
        this.securityService = securityService;
        this.exceptionService = exceptionService;
    }

    @Override
    public HashMap<String, String> login(String email, String pass) {
        HashMap<String, String> map = new HashMap<>();
        AccountModel account = accountRepository.findByEmail(email);
        if(account == null) exceptionService.throwEmailNonExistentException(email);
        boolean validPassword = securityService.passwordMatch(pass, account.getPassword());
        if(validPassword) {
            map.put("id", account.getAccount_ID().toString());
            map.put("name", account.getName());
        }

        return validPassword ? map : null;
    }

    @Override
    public void update(AccountModel model, AccountExtrasModel extrasModel) {
        if(model.getAccountExtras() == null) accountRepository.save(model.addExtras(extrasModel));
        else accountRepository.save(model.setExtras(extrasModel));
    }

    @Override
    public String create(AccountModel accountModel) {
        String encodedPass = securityService.hashPassword(accountModel.getPassword());
        accountModel.setPassword(encodedPass);
        return accountRepository.save(accountModel).getAccount_ID().toString();
    }

    @Override
    public void saveImage(MultipartFile multipartFile, AccountExtrasModel extrasModel) {

        String uniqueFileName = UUID.randomUUID() + multipartFile.getOriginalFilename();
        File file = new File(path, uniqueFileName);

        try {
            file.createNewFile();
            multipartFile.transferTo(file);
            if(ImageIO.read(file) == null || file.length() > (2000 * 1000)) {
                file.delete();
                exceptionService.throwInvalidImage();
            }
            extrasModel.setImageName(uniqueFileName);
        } catch (IOException ex) { ex.printStackTrace(); }
    }
}
