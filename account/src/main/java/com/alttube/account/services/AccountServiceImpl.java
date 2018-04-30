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

@Service
public class AccountServiceImpl implements AccountService {

    private final String path = Paths.get("").toAbsolutePath().toString() + "/images";
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
    public void login(String email, String pass) {
        AccountModel account = accountRepository.findByEmail(email);
        if(account == null) exceptionService.throwEmailNonExistentException(email);
        boolean validPassword = securityService.passwordMatch(pass, account.getPassword());
        if(!validPassword) exceptionService.throwInvalidPasswordException();
    }

    @Override
    public void update(AccountModel model, AccountExtrasModel extrasModel) {
        if(model.getAccountExtras() == null) accountRepository.save(model.addExtras(extrasModel));
        else accountRepository.save(model.setExtras(extrasModel));
    }

    @Override
    public void create(AccountModel accountModel) {
        String encodedPass = securityService.hashPassword(accountModel.getPassword());
        accountModel.setPassword(encodedPass);
        accountRepository.save(accountModel);
    }

    @Override
    public void saveImage(MultipartFile multipartFile, AccountExtrasModel extrasModel) {

        File file = new File(path, multipartFile.getOriginalFilename());

        try {
            file.createNewFile();
            multipartFile.transferTo(file);
            if(ImageIO.read(file) == null || file.length() > (1000 * 500)) {
                file.delete();
                exceptionService.throwInvalidImage();
            }
            System.out.println(file.length());
            extrasModel.setImageName(multipartFile.getOriginalFilename());
        } catch (IOException ex) { ex.printStackTrace(); }
    }
}
